package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

lateinit var submitPB:Button
private lateinit var taskRecylerView: RecyclerView
private lateinit var txtNoTasks: TextView

class ProjectDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)
        val buttonAddProjectTasks = findViewById<Button>(R.id.btnAddTasks)
        taskRecylerView = findViewById(R.id.rvTasks)
        txtNoTasks= findViewById(R.id.txtNoTasks)

        val taskList= mutableListOf<TaskListData>(
           TaskListData("t1","pending"),
            TaskListData("t2","pending"),
        )

//        val projectList= mutableListOf<ProjectListData>(
//           ProjectListData("t1","pending"),
//            ProjectListData("t2","pending"),
//       )

        val adapter1 = TaskListAdapter(taskList)
        taskRecylerView.adapter=adapter1
        taskRecylerView.layoutManager= LinearLayoutManager(this)
        var i=0;
        if (i==0){
            txtNoTasks.isVisible=true
            taskRecylerView.isVisible=false;
        }else {
            txtNoTasks.isVisible = false;
            taskRecylerView.isVisible = true;
        }
        buttonAddProjectTasks.setOnClickListener {
            Intent(this, ProjectsTasks::class.java).also {
                startActivity(it)
            }
            submitPB = findViewById(R.id.btnSubmitProject)
            submitPB.setOnClickListener {
                Intent(this, ListingProject::class.java).also {
                    finish()
                    startActivity(it)

                }
            }
        }
    }
}