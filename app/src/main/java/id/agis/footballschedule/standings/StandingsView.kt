package id.agis.footballschedule.standings

import id.agis.footballschedule.model.Standings

interface StandingsView {
    fun showStandings(data: List<Standings>, dataBadge: List<String?>)
    fun showLoading()
    fun hideLoading()
    fun onFailure(t: String)
}