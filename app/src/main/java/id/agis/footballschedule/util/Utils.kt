package id.agis.footballschedule.util

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(strDate: String, strTime: String): String{
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val date = formatter.parse("$strDate $strTime")
    return SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault()).format(date)
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}