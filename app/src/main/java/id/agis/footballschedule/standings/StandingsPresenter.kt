package id.agis.footballschedule.standings

import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.model.Standings
import id.agis.footballschedule.model.StandingsResponse
import id.agis.footballschedule.model.TeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingsPresenter(private val apiInterface: ApiInterface, val view: StandingsView) {
    lateinit var standingsBadgeList: Array<String?>
    var standingsList: MutableList<Standings> = mutableListOf()

    fun getStanding(idLeague: String) {
        view.showLoading()

        val getStanding = apiInterface.getStanding(idLeague)
        getStanding.enqueue(object : Callback<StandingsResponse> {
            override fun onFailure(call: Call<StandingsResponse>, t: Throwable) {
                view.onFailure(t.toString())
            }

            override fun onResponse(call: Call<StandingsResponse>, response: Response<StandingsResponse>) {
                standingsList.clear()
                standingsList.addAll(response.body()!!.table)
                standingsBadgeList = arrayOfNulls<String?>(standingsList.size)
                standingsList.forEachIndexed { i, s ->
                    getUrl(s.teamid, i)
                }

            }
        })
    }

    fun getUrl(idTeam: String, index: Int) {
        val getDetailTeam = apiInterface.getDetailTeam(idTeam)
        getDetailTeam.enqueue(object : Callback<TeamResponse> {
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                view.onFailure(t.toString())
                view.hideLoading()
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                val data = response.body()!!.teams
                if (data[0].strTeamBadge == null) {
                    standingsBadgeList[index] = "null"
                } else {
                    standingsBadgeList[index] = data[0].strTeamBadge!!
                }

                if (!standingsBadgeList.contains(null)) {
                    view.showStandings(standingsList, standingsBadgeList.toList())
                    view.hideLoading()
                }
            }
        })
    }
}