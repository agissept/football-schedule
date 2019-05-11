package id.agis.footballschedule.team

import id.agis.footballschedule.api.ApiInterface
import id.agis.footballschedule.model.TeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamPresenter(
    private val apiInterface: ApiInterface,
    private val view: TeamView
) {
    fun getTeam(idLeague: String) {
        view.showLoading()
        val getTeam: Call<TeamResponse> = apiInterface.getTeam(idLeague)
        getTeam.enqueue(object : Callback<TeamResponse> {
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                view.onFailure(t.toString())
            }
            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                view.showTeam(response.body()!!.teams)
                view.hideLoading()
            }

        })
    }
}