package id.agis.footballschedule.detail_league

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.footballschedule.R
import id.agis.footballschedule.model.Event
import kotlinx.android.synthetic.main.match_list.view.*

class MatchAdapter(private val matchList: List<Event>,
                   private val context: Context,
                   private val homeBadge: List<String?>,
                   private val awayBadge: List<String?>) :
    RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_list, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = matchList[position]

        holder.title.text = event.strEvent
        holder.date.text = event.strDate
        holder.homeName.text = event.strHomeTeam
        holder.awayName.text = event.strAwayTeam
        if (event.intHomeScore != null) {
            holder.homeScore.text = event.intHomeScore
            holder.awayScore.text = event.intAwayScore
        }
            Glide.with(context).load(homeBadge[position]).placeholder(R.drawable.ic_trophy).into(holder.homeBadge)
            Glide.with(context).load(awayBadge[position]).placeholder(R.drawable.ic_trophy).into(holder.awayBadge)

    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val date: TextView = itemView.date
        val homeName: TextView = itemView.home_name
        val awayName: TextView = itemView.away_name
        val homeScore: TextView = itemView.home_score
        val awayScore: TextView = itemView.away_score
        val homeBadge: ImageView = itemView.home_badge
        val awayBadge: ImageView = itemView.away_badge

    }


}





