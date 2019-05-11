package id.agis.footballschedule.model

data class Standings(
    val name: String,
    val teamid: String,
    val played: String,
    val goalsfor: String,
    val goalsagainst: String,
    val goalsdifference: String,
    val win: String,
    val draw: String,
    val loss: String,
    val total: String
)