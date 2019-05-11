package id.agis.footballschedule.standings

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
import id.agis.footballschedule.model.Standings
import id.agis.footballschedule.R
import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface
import kotlinx.android.synthetic.main.fragment_standings.view.*


class StandingsFragment(private val idLeague: String) : Fragment(), StandingsView {

    private val standingsList: MutableList<Standings> = mutableListOf()
    private var standingsListBadge: MutableList<String?> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: StandingsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_standings, container, false)
        println("Hello standing")
        recyclerView = view.recycler_view
        progressBar = view.progress_circular
        swipeRefresh = view.swipe_refresh

        adapter = StandingsAdapter(standingsList, standingsListBadge, context!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val presenter = StandingsPresenter(apiInterface, this)
        presenter.getStanding(idLeague)

        swipeRefresh.setOnRefreshListener {
            presenter.getStanding(idLeague)
            progressBar.visibility = View.INVISIBLE
        }

        return view
    }

    override fun showStandings(data: List<Standings>, dataBadge: List<String?>) {
        standingsList.clear()
        standingsList.addAll(data)
        standingsListBadge.clear()
        standingsListBadge.addAll(dataBadge)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        progressBar.visibility = View.INVISIBLE
    }

    override fun onFailure(t: String) {
        Toast.makeText(context, t, Toast.LENGTH_SHORT).show()
        println(t)
    }
}