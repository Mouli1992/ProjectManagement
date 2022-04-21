package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

lateinit var submitPB:Button

class ProjectDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)
        val buttonAddProjectTasks = findViewById<Button>(R.id.btnAddTasks)
        buttonAddProjectTasks.setOnClickListener {
            Intent(this, ProjectsTasks::class.java).also {
                startActivity(it)
            }
            submitPB = findViewById(R.id.btnSubmitProject)
            submitPB.setOnClickListener {
                Intent(this, ListingProject::class.java).also {
                    startActivity(it)

                }
            }
        }
    }
}