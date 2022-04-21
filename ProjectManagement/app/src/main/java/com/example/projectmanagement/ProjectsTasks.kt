package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private lateinit var buttonSubmitTask: Button



class ProjectsTasks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects_tasks)
        buttonSubmitTask= findViewById<Button>(R.id.btnSubmitTask)
        buttonSubmitTask.setOnClickListener{
            Intent(this,ProjectDetails::class.java).also {
                startActivity(it)
            }
        }
    }
}