package id.agis.footballschedule.api

import id.agis.footballschedule.model.LeagueResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("search_all_leagues.php?s=Soccer")
    fun getLeague(): Call<LeagueResponse>

}