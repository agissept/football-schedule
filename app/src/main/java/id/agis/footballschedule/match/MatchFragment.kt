package id.agis.footballschedule.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.agis.footballschedule.R
import id.agis.footballschedule.model.Event
import kotlinx.android.synthetic.main.fragment_match.view.*
import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface


class MatchFragment(private val idLeague: String) : Fragment(), MatchView {

    private var nextMatchList: MutableList<Event> = mutableListOf()
    private var lastMatchList: MutableList<Event> = mutableListOf()
    private var homeBadgeNextEventList: MutableList<String?> = mutableListOf()
    private var awayBadgeNextEventList: MutableList<String?> = mutableListOf()
    private var homeBadgeLastEventList: MutableList<String?> = mutableListOf()
    private var awayBadgeLastEventList: MutableList<String?> = mutableListOf()

    private lateinit var progressBar: ProgressBar
    private lateinit var nextMatch: RecyclerView
    private lateinit var lastMatch: RecyclerView
    private lateinit var nextMatchAdapter: MatchAdapter
    private lateinit var lastMatchAdapter: MatchAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)

        progressBar = view.progress_circular
        swipeRefresh = view.swipe_refresh
        nextMatch = view.next_match
        lastMatch = view.last_match

        nextMatch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lastMatch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        nextMatch.isNestedScrollingEnabled = false
        lastMatch.isNestedScrollingEnabled = false

        nextMatchAdapter = MatchAdapter(nextMatchList, context!!, homeBadgeNextEventList, awayBadgeNextEventList)
        lastMatchAdapter = MatchAdapter(lastMatchList, context!!, homeBadgeLastEventList, awayBadgeLastEventList)

        nextMatch.adapter = nextMatchAdapter
        lastMatch.adapter = lastMatchAdapter

        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val presenter = MatchPresenter(apiInterface, this)
        presenter.getNextMatch(idLeague)
        presenter.getLastMatch(idLeague)

        swipeRefresh.setOnRefreshListener {
            presenter.getNextMatch(idLeague)
            presenter.getLastMatch(idLeague)
            progressBar.visibility = View.INVISIBLE
        }

        return view
    }


    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showNextMatch(match: List<Event>, homeBadge: List<String?>, awayBadge: List<String?>) {
        swipeRefresh.isRefreshing = false
        nextMatchList.clear()
        homeBadgeNextEventList.clear()
        awayBadgeNextEventList.clear()
        nextMatchList.addAll(match)
        homeBadgeNextEventList.addAll(homeBadge)
        awayBadgeNextEventList.addAll(awayBadge)
        nextMatchAdapter.notifyDataSetChanged()
    }

    override fun showLastMatch(match: List<Event>, homeBadge: List<String?>, awayBadge: List<String?>) {
        swipeRefresh.isRefreshing = false
        lastMatchList.clear()
        homeBadgeLastEventList.clear()
        awayBadgeLastEventList.clear()
        lastMatchList.addAll(match)
        homeBadgeLastEventList.addAll(homeBadge)
        awayBadgeLastEventList.addAll(awayBadge)
        homeBadgeLastEventList.forEachIndexed { i, s ->
            println("$i homebadge = $s")
        }
        lastMatchAdapter.notifyDataSetChanged()
    }

    override fun onFailure(t: String) {
        Toast.makeText(context, "error $t", Toast.LENGTH_SHORT).show()
    }
}