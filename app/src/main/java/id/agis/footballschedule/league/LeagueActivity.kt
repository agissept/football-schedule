package id.agis.footballschedule.league

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.agis.footballschedule.R
import id.agis.footballschedule.api.ApiClient
import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.model.LeagueResponse
import kotlinx.android.synthetic.main.league_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeagueActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.league_activity)

        recyclerView = recycler_view
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val apiClient = ApiClient.retrofit.create(ApiInterface::class.java)
        val call: Call<LeagueResponse> = apiClient.getLeague()

        call.enqueue(object: Callback<LeagueResponse>{


            override fun onResponse(call: Call<LeagueResponse>, response: Response<LeagueResponse>) {
                recyclerView.adapter = LeagueAdapter(response.body()?.countrys!!, applicationContext)
            }

            override fun onFailure(call: Call<LeagueResponse>, t: Throwable) {

            }

        })



    }
}
