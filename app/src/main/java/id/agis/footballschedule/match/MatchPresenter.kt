package id.agis.footballschedule.match

import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.model.Event
import id.agis.footballschedule.model.EventResponse
import id.agis.footballschedule.model.TeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchPresenter(private val apiInterface: ApiInterface, val view: MatchView) {
    lateinit var homeBadgeNextEventList: Array<String?>
    lateinit var awayBadgeNextEventList: Array<String?>
    lateinit var homeBadgeLastEventList: Array<String?>
    lateinit var awayBadgeLastEventList: Array<String?>
    var nextMatchList: MutableList<Event> = mutableListOf()
    var lastMatchList: MutableList<Event> = mutableListOf()

    fun getNextMatch(idLeague: String) {
        view.showLoading()
        homeBadgeNextEventList = arrayOfNulls(0)
        awayBadgeNextEventList = arrayOfNulls(0)
        val getNextMatch: Call<EventResponse> = apiInterface.getNextMatch(idLeague)
        getNextMatch.enqueue(object : Callback<EventResponse> {
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                view.onFailure(t.toString())
            }

            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                val a = response.body()!!.events
                if (a != null) {
                    view.hideLoading()
                    nextMatchList.clear()
                    nextMatchList.addAll(a)
                    homeBadgeNextEventList = arrayOfNulls<String?>(nextMatchList.size)
                    awayBadgeNextEventList = arrayOfNulls<String?>(nextMatchList.size)
                    println("size n = " + a.size)

                    nextMatchList.forEachIndexed { index, e ->
                        getUrlNextMatch(e.idHomeTeam, index, "home")
                        getUrlNextMatch(e.idAwayTeam, index, "away")
                    }

                } else {
                    println("data is null")
                }
            }

        })
    }

    fun getLastMatch(idLeague: String) {
        view.showLoading()
        homeBadgeLastEventList = arrayOfNulls(0)
        awayBadgeLastEventList = arrayOfNulls(0)
        val getLastMatch: Call<EventResponse> = apiInterface.getLastMatch(idLeague)
        getLastMatch.enqueue(object : Callback<EventResponse> {
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                view.onFailure(t.toString())
            }

            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                val a = response.body()!!.events
                if (a != null) {
                    lastMatchList.clear()
                    lastMatchList.addAll(a)
                    homeBadgeLastEventList = arrayOfNulls<String?>(lastMatchList.size)
                    awayBadgeLastEventList = arrayOfNulls<String?>(lastMatchList.size)
                    lastMatchList.forEachIndexed { index, e ->
                        getUrlLastMatch(e.idHomeTeam, index, "home")
                        getUrlLastMatch(e.idAwayTeam, index, "away")
                    }
                } else {
                    println("data is null")
                }
            }

        })
    }

    private fun getUrlNextMatch(idTeam: String, index: Int, status: String) {
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val getDetailTeam: Call<TeamResponse> = apiInterface.getDetailTeam(idTeam)

        getDetailTeam.enqueue(object : Callback<TeamResponse> {
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                val a = response.body()!!.teams
                if (status == "home") {
                    if (a[0].strTeamBadge == null) {
                        homeBadgeNextEventList[index] = "aaa"
                    } else {
                        homeBadgeNextEventList[index] = a[0].strTeamBadge!!
                    }

                }
                if (status == "away") {
                    if (a[0].strTeamBadge == null) {
                        awayBadgeNextEventList[index] = "aaa"
                    } else {
                        awayBadgeNextEventList[index] = a[0].strTeamBadge!!
                    }

                }
                println("size = ${nextMatchList.size}  ${homeBadgeNextEventList.size} ${awayBadgeNextEventList.size}")

                if (homeBadgeNextEventList.size == nextMatchList.size && awayBadgeNextEventList.size == nextMatchList.size) {
                    view.showNextMatch(nextMatchList, homeBadgeNextEventList.toList(), awayBadgeNextEventList.toList())
                    view.hideLoading()

                }
            }
        })
    }

    private fun getUrlLastMatch(idTeam: String, index: Int, status: String) {
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val getDetailTeam: Call<TeamResponse> = apiInterface.getDetailTeam(idTeam)

        getDetailTeam.enqueue(object : Callback<TeamResponse> {
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                val a = response.body()!!.teams
                if (status == "home") {
                    if (a[0].strTeamBadge == null) {
                        homeBadgeLastEventList[index] = "aaa"
                    } else {
                        homeBadgeLastEventList[index] = a[0].strTeamBadge!!
                    }
                }
                if (status == "away") {
                    if (a[0].strTeamBadge == null) {
                        awayBadgeLastEventList[index] = "aaa"
                    } else {
                        awayBadgeLastEventList[index] = a[0].strTeamBadge!!
                    }
                }
                println("size = ${lastMatchList.size}  ${homeBadgeNextEventList.size} ${awayBadgeNextEventList.size}")

                if (homeBadgeLastEventList.size == lastMatchList.size && awayBadgeLastEventList.size == lastMatchList.size) {
                    view.showLastMatch(lastMatchList, homeBadgeLastEventList.toList(), awayBadgeLastEventList.toList())
                    view.hideLoading()
                }
            }
        })
    }


}