package id.agis.footballschedule.standings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.footballschedule.R
import id.agis.footballschedule.model.Standings
import kotlinx.android.synthetic.main.standings_list.view.*

class StandingsAdapter(
    private val standingsList: List<Standings>,
    private val standingsListBadge: List<String?>,
    private val context: Context
) :
    RecyclerView.Adapter<StandingsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.standings_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return standingsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = standingsList[position]

        holder.name.text = s.name
        holder.played.text = s.played
        holder.goalFor.text = s.goalsfor
        holder.goalAgainst.text = s.goalsagainst
        holder.goalDifference.text = s.goalsdifference
        holder.win.text = s.win
        holder.draw.text = s.draw
        holder.loss.text = s.loss
        holder.total.text = s.total
        if(standingsListBadge[position] != "null"){
            Glide.with(context).load(standingsListBadge[position]).placeholder(R.drawable.ic_trophy).into(holder.badge)
        }else{
            Glide.with(context).load(R.drawable.ic_trophy).into(holder.badge)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.name
        val played:TextView = itemView.played
        val goalFor:TextView = itemView.goal_for
        val goalAgainst:TextView = itemView.goal_against
        val goalDifference:TextView = itemView.goal_difference
        val win:TextView = itemView.win
        val draw:TextView = itemView.draw
        val loss:TextView = itemView.loss
        val total:TextView = itemView.total
        val badge:ImageView = itemView.badge
    }
}