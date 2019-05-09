package id.agis.footballschedule.league

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.footballschedule.R
import id.agis.footballschedule.detail_league.DetailLeagueActivity
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

        holder.cardView.setOnClickListener {
            val i = Intent(it.context, DetailLeagueActivity::class.java)
            i.putExtra("idLeague", l.idLeague)
            it.context.startActivity(i)
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val leagueName: TextView = itemView.league_name
        val leagueBadge: ImageView = itemView.league_badge
        val cardView: CardView = itemView.card_view
    }


}