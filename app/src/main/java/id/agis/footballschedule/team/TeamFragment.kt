package id.agis.footballschedule.team

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
import id.agis.footballschedule.model.Team
import kotlinx.android.synthetic.main.fragment_team.view.*
import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface

class TeamFragment(private val idLeague: String) : Fragment(), TeamView {

    private var teamList: MutableList<Team> = mutableListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: TeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        swipeRefresh = view.swipe_refresh
        recyclerView = view.recycler_view
        progressBar = view.progress_circular

        adapter = TeamAdapter(teamList, context!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val presenter = TeamPresenter(apiInterface, this)
        presenter.getTeam(idLeague)

        swipeRefresh.setOnRefreshListener {
            presenter.getTeam(idLeague)
            swipeRefresh.isRefreshing = false
        }

        return view
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        progressBar.visibility = View.INVISIBLE
    }

    override fun showTeam(data: List<Team>) {
        teamList.clear()
        teamList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onFailure(t: String) {
        Toast.makeText(context, t, Toast.LENGTH_SHORT).show()
        println(t)
    }
}
