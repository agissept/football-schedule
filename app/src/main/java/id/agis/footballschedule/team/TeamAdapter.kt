package id.agis.footballschedule.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agis.footballschedule.R
import id.agis.footballschedule.model.Team
import kotlinx.android.synthetic.main.team_list.view.*

class TeamAdapter(
    private val listTeam: List<Team>,
    private val context: Context
): RecyclerView.Adapter<TeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTeam.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val t = listTeam[position]
        holder.name.text = t.strTeam
        holder.description.text= t.strDescriptionEN
        Glide.with(context).load(t.strTeamBadge).placeholder(R.drawable.ic_trophy).into(holder.badge)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.name
        val description: TextView = itemView.description
        val badge: ImageView = itemView.badge
    }
}