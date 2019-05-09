package id.agis.footballschedule.match

import id.agis.footballschedule.model.Event

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatch(match: List<Event>, homeBadge: List<String?>, awayBadge: List<String?>)
    fun showLastMatch(match: List<Event>,  homeBadge: List<String?>, awayBadge: List<String?>)
    fun onFailure(t :String)
}