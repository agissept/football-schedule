package id.agis.footballschedule.detail_league

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.agis.footballschedule.R
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import id.agis.footballschedule.StandingFragment
import id.agis.footballschedule.team.TeamFragment
import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.match.MatchFragment
import id.agis.footballschedule.model.DetailLeagueResponse
import kotlinx.android.synthetic.main.activity_detail_league.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailLeagueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbarLayout.title = "Detail League"

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.abc_primary_text_material_dark)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.mtrl_btn_transparent_bg_color)
        )

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }



        val viewPagerAdapter = TabAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(MatchFragment(intent.getStringExtra("idLeague")), "Match")
        viewPagerAdapter.addFragment(StandingFragment(), "Standing")
        viewPagerAdapter.addFragment(TeamFragment(intent.getStringExtra("idLeague")), "Team")
        view_pager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)


        getDetailLeague()

    }

    private fun getDetailLeague() {
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        val getDetailLeague: Call<DetailLeagueResponse> =
            apiInterface.getDetailLeague(intent.getStringExtra("idLeague"))
        getDetailLeague.enqueue(object : Callback<DetailLeagueResponse> {
            override fun onFailure(call: Call<DetailLeagueResponse>, t: Throwable) {
                Toast.makeText(this@DetailLeagueActivity.applicationContext,"data gagal dimuat $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DetailLeagueResponse>, response: Response<DetailLeagueResponse>) {
                val detailLeague = response.body()?.leagues
                Glide.with(applicationContext).load(detailLeague!![0].strBadge).placeholder(R.drawable.ic_trophy)
                    .into(league_badge)
                league_name.text = detailLeague[0].strLeague
                league_description.text = detailLeague[0].strDescriptionEN

            }

        })
    }




}
