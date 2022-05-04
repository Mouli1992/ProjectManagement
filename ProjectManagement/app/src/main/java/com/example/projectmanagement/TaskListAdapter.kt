package com.example.projectmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagement.model.TaskDetails
import java.nio.file.Files.size

class TaskListAdapter (
    var taskdata: List<TaskDetails>
        ):RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>(){
    inner class TaskListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewtasklist,parent,false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.itemView.apply {
            val txtbutton = findViewById<TextView>(R.id.txtTaskName)
            val txtstatus = findViewById<TextView>(R.id.txtTaskStatus)

            txtbutton.text= taskdata[position].taskName
            txtstatus.text= taskdata[position].taskStatus


        }
    }

    override fun getItemCount(): Int {
        return taskdata.size
    }
}