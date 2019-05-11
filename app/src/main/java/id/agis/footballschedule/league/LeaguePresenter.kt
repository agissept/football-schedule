package id.agis.footballschedule.league

import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.model.LeagueResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaguePresenter(val view: LeagueView, val apiInterface: ApiInterface) {
    fun getLeague(){
        view.showLoading()
        val getLeague: Call<LeagueResponse> = apiInterface.getLeague()

        getLeague.enqueue(object: Callback<LeagueResponse> {

            override fun onResponse(call: Call<LeagueResponse>, response: Response<LeagueResponse>) {
                view.showLeague(response.body()!!.countrys)
                view.hideLoading()
            }

            override fun onFailure(call: Call<LeagueResponse>, t: Throwable) {
                view.hideLoading()
            }

        })
    }
}