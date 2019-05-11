package id.agis.footballschedule.team

import id.agis.footballschedule.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeam(data: List<Team>)
    fun onFailure(t :String)
}
