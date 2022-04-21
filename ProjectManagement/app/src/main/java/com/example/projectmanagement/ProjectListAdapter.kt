package com.example.projectmanagement

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagement.R.layout.recyclerviewprojectlist


class ProjectListAdapter(
    var projectdata: List<ProjectListData>
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

           txtbutton.text= projectdata[position].title
           txtstatus.text=projectdata[position].status


       }
    }

    override fun getItemCount(): Int {
        return  projectdata.size
    }
}