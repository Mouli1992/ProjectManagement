package com.example.projectmanagement

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagement.R.layout.recyclerviewprojectlist
import com.example.projectmanagement.model.ProjectDetails


class ProjectListAdapter(
    var projectdata: List<ProjectDetails>
) : RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder>(){
    inner class ProjectListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(recyclerviewprojectlist,parent,false)
        return ProjectListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {
       holder.itemView.apply {
           val txtbutton = findViewById<TextView>(R.id.txtProjectName)
           val txtstatus = findViewById<TextView>(R.id.txtProjectStatus)
           val txtProjectDeadline = findViewById<TextView>(R.id.txtProjectDeadline)

           txtbutton.text= projectdata[position].projectName
           txtstatus.text=projectdata[position].projectStatus
           txtProjectDeadline.text = projectdata[position].projectDeadline?.toDate().toString()

       }
    }

    override fun getItemCount(): Int {
        return  projectdata.size
    }
}