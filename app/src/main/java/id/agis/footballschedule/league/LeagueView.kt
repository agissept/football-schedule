package id.agis.footballschedule.league

import id.agis.footballschedule.model.League

interface LeagueView {
    fun showLeague(data: List<League>)
    fun showLoading()
    fun hideLoading()
    fun onFailure(t: String)
}