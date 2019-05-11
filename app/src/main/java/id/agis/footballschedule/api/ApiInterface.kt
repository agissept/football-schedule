package id.agis.footballschedule.api

import id.agis.footballschedule.model.DetailLeagueResponse
import id.agis.footballschedule.model.EventResponse
import id.agis.footballschedule.model.LeagueResponse
import id.agis.footballschedule.model.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search_all_leagues.php?s=Soccer")
    fun getLeague(): Call<LeagueResponse>

    @GET("lookupleague.php")
    fun getDetailLeague(@Query("id") idLeague: String): Call<DetailLeagueResponse>

    @GET("eventsnextleague.php")
    fun getNextMatch(@Query("id") idLeague: String): Call<EventResponse>

    @GET("eventspastleague.php")
    fun getLastMatch(@Query("id") idLeague: String): Call<EventResponse>

    @GET("lookupteam.php")
    fun getDetailTeam(@Query ("id") idTeam: String): Call<TeamResponse>

    @GET("lookup_all_teams.php")
    fun getTeam(@Query ("id") idLeague: String): Call<TeamResponse>

}