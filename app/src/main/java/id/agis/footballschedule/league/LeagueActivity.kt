package id.agis.footballschedule.league

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.agis.footballschedule.R
import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.model.League
import kotlinx.android.synthetic.main.league_activity.*

class LeagueActivity : AppCompatActivity(), LeagueView {

    private var leagueList: MutableList<League> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LeagueAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: LeaguePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.league_activity)

        recyclerView = recycler_view
        swipeRefresh = swipe_refresh
        progressBar = progress_circular

        adapter = LeagueAdapter(leagueList, this)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)

        presenter = LeaguePresenter(this, apiInterface)
        presenter.getLeague()

        swipeRefresh.setOnRefreshListener {
            presenter.getLeague()
            hideLoading()
        }


    }

    override fun showLeague(data: List<League>) {
        swipeRefresh.isRefreshing = false
        leagueList.clear()
        leagueList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onFailure(t: String) {
        Toast.makeText(applicationContext, "error : $t", Toast.LENGTH_SHORT).show()
    }
}
