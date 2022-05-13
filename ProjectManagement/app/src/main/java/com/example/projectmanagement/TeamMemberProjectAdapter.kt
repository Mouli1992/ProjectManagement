package com.example.projectmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagement.R.layout.recyclerviewteammembertasks
import com.example.projectmanagement.model.ProjectDetails

class TeamMemberProjectAdapter(
    var teammembertaskdata: List<TeamMemberTaskList>) : RecyclerView.Adapter<TeamMemberProjectAdapter.TeamMemberProjectViewHolder>(){
    inner class TeamMemberProjectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  TeamMemberProjectAdapter.TeamMemberProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(recyclerviewteammembertasks,parent,false)
        return TeamMemberProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamMemberProjectAdapter.TeamMemberProjectViewHolder, position: Int) {

        holder.itemView.apply {
            val txttaskname = findViewById<TextView>(R.id.txtTeamMemberTaskName)
            val txtstatus = findViewById<TextView>(R.id.txtTeamMemberTaskStatus)

//            txtbutton.text= teammembertaskdata[position].
//            txtstatus.text=teammembertaskdata[position].
//            txtProjectDeadline.text = teammembertaskdata[position].?.toDate().toString()

        }
    }

    override fun getItemCount(): Int {
        return  teammembertaskdata.size
    }



}