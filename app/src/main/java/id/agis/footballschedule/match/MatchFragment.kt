package id.agis.footballschedule.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.agis.footballschedule.R
import id.agis.footballschedule.model.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.detail_league.MatchAdapter
import id.agis.footballschedule.model.Event
import id.agis.footballschedule.model.TeamResponse
import kotlinx.android.synthetic.main.fragment_match.view.*


lateinit var nextMatch: RecyclerView
lateinit var lastMatch: RecyclerView


class MatchFragment(private val idLeague: String) : Fragment() {
    var nextMatchList = mutableListOf<Event>()
    var lastMatchList = mutableListOf<Event>()
    var homeBadgeNextEventList: Array<String?> = arrayOfNulls(0)
    var awayBadgeNextEventList: Array<String?> = arrayOfNulls(0)
    var homeBadgeLastEventList: Array<String?> = arrayOfNulls(0)
    var awayBadgeLastEventList: Array<String?> = arrayOfNulls(0)
    var nextMatchStatus = false
    var lastMatchStatus = false
    lateinit var progressBar: ProgressBar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)

       progressBar = view.progress_circular
        progressBar.visibility = View.VISIBLE


        nextMatch = view.next_match
        lastMatch = view.last_match

        nextMatch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lastMatch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        nextMatch.isNestedScrollingEnabled = false
        lastMatch.isNestedScrollingEnabled = false

        getData()

        view.swipe_refresh.setOnRefreshListener {
            getData()
            view.swipe_refresh.isRefreshing = false
        }


        return view
    }

    private fun getData() {
        getLastMatch()
        getNextMatch()


    }

    private fun getNextMatch() {
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val getNextMatch: Call<EventResponse> = apiInterface.getNextMatch(idLeague)
        getNextMatch.enqueue(object : Callback<EventResponse> {
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                println("Throwable : $t")
            }

            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                val a = response.body()!!.events
                if (a != null) {
                    nextMatchList.addAll(a)
                    homeBadgeNextEventList = arrayOfNulls<String?>(nextMatchList.size)
                    awayBadgeNextEventList = arrayOfNulls<String?>(nextMatchList.size)
                    println("size n = " + nextMatchList.size)

                    nextMatchList.forEachIndexed { index, e ->
                        getUrl(e.idHomeTeam, index, "home", "next")
                        getUrl(e.idAwayTeam, index, "away", "next")
                    }

                } else {
                    println("data is null")
                    nextMatchStatus = true
                    finishProgressBar()

                }
            }

        })
    }


    private fun getLastMatch() {
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val getLastMatch: Call<EventResponse> = apiInterface.getLastMatch(idLeague)
        getLastMatch.enqueue(object : Callback<EventResponse> {
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                val a = response.body()!!.events
                if (a != null) {
                    println("data is not null")
                    lastMatchList.addAll(a)
                    homeBadgeLastEventList = arrayOfNulls<String?>(lastMatchList.size)
                    awayBadgeLastEventList = arrayOfNulls<String?>(lastMatchList.size)
                    lastMatchList.forEachIndexed { index, e ->
                        getUrl(e.idHomeTeam, index, "home", "last")
                        getUrl(e.idAwayTeam, index, "away", "last")
                    }
                    println("size l = " + lastMatchList.size)
                } else {
                    lastMatchStatus = true
                    finishProgressBar()
                    println("data is null")
                }
            }

        })
    }

    private fun getUrl(idTeam: String, index: Int, status: String, time: String) {
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val getDetailTeam: Call<TeamResponse> = apiInterface.getDetailTeam(idTeam)

        getDetailTeam.enqueue(object : Callback<TeamResponse> {
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                val a = response.body()!!.teams
                if (status == "home") {
                    if (time == "next") {
                        if (a[0].strTeamBadge == null) {
                            homeBadgeNextEventList[index] = "null"
                        } else {
                            homeBadgeNextEventList[index] = a[0].strTeamBadge!!
                        }
                    }
                    if (time == "last") {
                        if (a[0].strTeamBadge == null) {
                            homeBadgeLastEventList[index] = "null"
                        } else {
                            homeBadgeLastEventList[index] = a[0].strTeamBadge!!
                        }
                    }
                }
                if (status == "away") {
                    if (time == "next") {
                        if (a[0].strTeamBadge == null) {
                            awayBadgeNextEventList[index] = "null"
                        } else {
                            awayBadgeNextEventList[index] = a[0].strTeamBadge!!
                        }
                    }
                    if (time == "last") {
                        if (a[0].strTeamBadge == null) {
                            awayBadgeLastEventList[index] = "null"
                        } else {
                            awayBadgeLastEventList[index] = a[0].strTeamBadge!!
                        }
                    }
                }
                println("size = ${nextMatchList.size}  ${homeBadgeNextEventList.size} ${awayBadgeNextEventList.size}")

                if (homeBadgeNextEventList.size == nextMatchList.size && awayBadgeNextEventList.size == nextMatchList.size) {
                    nextMatch.adapter = MatchAdapter(
                        nextMatchList,
                        this@MatchFragment.context!!,
                        homeBadgeNextEventList.toList(),
                        awayBadgeNextEventList.toList()
                    )
                    lastMatchStatus = true
                    finishProgressBar()
                }
                if (homeBadgeLastEventList.size == lastMatchList.size && awayBadgeLastEventList.size == lastMatchList.size) {
                    lastMatch.adapter = MatchAdapter(
                        lastMatchList,
                        this@MatchFragment.context!!,
                        homeBadgeLastEventList.toList(),
                        awayBadgeLastEventList.toList()
                    )
                    nextMatchStatus = true
                    finishProgressBar()
                }
            }
        })
    }

    fun finishProgressBar(){
        if(nextMatchStatus && lastMatchStatus){
            progressBar.visibility = View.INVISIBLE
        }
    }


}