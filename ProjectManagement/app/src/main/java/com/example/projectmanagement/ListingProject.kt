package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button as Button
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingProject : AppCompatActivity() {
    private lateinit var projectRecylerView: RecyclerView
    private lateinit var txtNo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_project)

        projectRecylerView = findViewById(R.id.rvProjectShown)
        txtNo= findViewById(R.id.txtNoProjects)

        val todoList= mutableListOf<ProjectListData>(
            ProjectListData("p1","pending"),
            ProjectListData("p2","pending"),
        )

        val adapter = ProjectListAdapter(todoList)
        projectRecylerView.adapter=adapter
        projectRecylerView.layoutManager=LinearLayoutManager(this)
        var i=0;
        if (i==0){
            txtNo.isVisible=true
            projectRecylerView.isVisible=false;
        }else {
            txtNo.isVisible = false;
            projectRecylerView.isVisible = true;
        }

//        val fragmentone = NoProjectFragment()
//        val fragmenttwo = ProjectListFragment()

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.flFragmentListing,fragmentone)
//            commit()
//        }
        val buttonAddProject = findViewById<Button>(R.id.btnAddProject)

        buttonAddProject.setOnClickListener{
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.flFragmentListing,fragmenttwo)
//                commit()
//                }
            val intent = Intent(this, ProjectDetails:: class.java).also {
                startActivity(it)
            }

            }

        }
    }
