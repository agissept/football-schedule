package id.agis.footballschedule.league

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.footballschedule.R
import id.agis.footballschedule.model.League
import kotlinx.android.synthetic.main.league_list.view.*

class LeagueAdapter(
    private val leagueList: List<League>,
    private val context: Context
): RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.league_list, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leagueList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val l = leagueList[position]
        holder.leagueName.text = l.strLeague
        Glide.with(context).load(l.strBadge).placeholder(R.drawable.ic_trophy).into(holder.leagueBadge)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val leagueName: TextView = itemView.league_name
        val leagueBadge: ImageView = itemView.league_badge
    }
}