package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button as Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.TaskDetails
import com.example.projectmanagement.model.UserDetails
import com.example.projectmanagement.utils.PROJECT_STATUS_PENDING
import com.example.projectmanagement.utils.TASK_STATUS_ONGOING
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ListingProject : AppCompatActivity() {
    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var txtNo: TextView
    private val database = Firebase.firestore
    private lateinit var buttonAddProject : Button
    private lateinit var listOfTeamMembers : MutableList<String>
    private lateinit var listOfProjectDetails: MutableList<ProjectDetails>
    companion object{
        private const val TAG = "ListingProject"
        private const val CREATE_REQUEST_CODE = 248

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_project)

        projectRecyclerView = findViewById(R.id.rvProjectShown)
        txtNo = findViewById(R.id.txtNoProjects)

        val todoList = mutableListOf<ProjectListData>(
            ProjectListData("p1", "pending"),
            ProjectListData("p2", "pending"),
        )

        listOfTeamMembers = getTeamMemberDetails()
        listOfProjectDetails = getManagerProjectDetails()


        val adapter = ProjectListAdapter(todoList)
        projectRecyclerView.adapter = adapter
        projectRecyclerView.layoutManager = LinearLayoutManager(this)
        var i = 0;
        if (i == 0) {
            txtNo.isVisible = true
            projectRecyclerView.isVisible = false;
        } else {
            txtNo.isVisible = false;
            projectRecyclerView.isVisible = true;
        }

//        val fragmentone = NoProjectFragment()
//        val fragmenttwo = ProjectListFragment()

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.flFragmentListing,fragmentone)
//            commit()
//        }
        buttonAddProject = findViewById<Button>(R.id.btnAddProject)

        buttonAddProject.setOnClickListener{
            val projectTaskDetails : ProjectDetails = createDummyProjectDetails()
            database.collection("projectDetails").document(projectTaskDetails.projectId.toString()).
            set(projectTaskDetails).addOnCompleteListener { projectTaskDetailsTask ->
                if(projectTaskDetailsTask.isSuccessful) {
                    Toast.makeText(this, "Project Details Added Successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Log.e(ListingProject.TAG, "Exception with User Registration")
                    Toast.makeText(this, "Project Details Insertion Failed", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }

            }

        }

    }

    private fun getManagerProjectDetails(): MutableList<ProjectDetails> {
        var projectDetailsLst = mutableListOf<ProjectDetails>()
        database.collection("projectDetails").
        whereEqualTo("projectCreatedBy","C@live.com").get().addOnSuccessListener { documents ->
            projectDetailsLst = documents.toObjects(ProjectDetails::class.java)
        }

        return projectDetailsLst;

    }

    private fun getTeamMemberDetails(): MutableList<String> {
        var teamMemberEmailLst: MutableList<String> = mutableListOf()
        var userDetailsLst = mutableListOf<UserDetails>()
        database.collection("userDetails").
        whereEqualTo("role","Team Member").get().addOnSuccessListener { documents ->
            userDetailsLst = documents.toObjects(UserDetails::class.java)
        }

        for(userDetails in userDetailsLst){
            teamMemberEmailLst.add(userDetails?.email!!)
            }

        return teamMemberEmailLst
        }

    }

//        val buttonAddProject = findViewById<Button>(R.id.btnAddProject)
//
//        buttonAddProject.setOnClickListener{
////            supportFragmentManager.beginTransaction().apply {
////                replace(R.id.flFragmentListing,fragmenttwo)
////                commit()
////                }
//            val intent = Intent(this, ProjectDetails:: class.java).also {
//                startActivity(it)
//            }




//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.flFragmentListing,fragmenttwo)
//                commit()
//                }
            //Commented by Chandra to test project task Additon
//            val intent = Intent(this, ProjectDetails:: class.java).also {
//                startActivity(it)




    private fun createDummyProjectDetails(): ProjectDetails {
        var taskLst = mutableListOf<TaskDetails>()
        var taskDetails = TaskDetails(
            Integer.toUnsignedLong (Timestamp.now().nanoseconds),
            "TasKName1",
            TASK_STATUS_ONGOING,
            Timestamp.now(),
            "C@live1.com"
        )
        taskLst.add(taskDetails)
        taskDetails = TaskDetails(
            Date().getTime(),
            "TasKName2",
            TASK_STATUS_ONGOING,
            Timestamp.now(),
            "C@live2.com"
        )
        taskLst.add(taskDetails)
        taskDetails = TaskDetails(
            Date().getTime(),
            "TasKName3",
            TASK_STATUS_ONGOING,
            Timestamp.now(),
            "C@live3.com"
        )
        taskLst.add(taskDetails)

        return ProjectDetails(
            Date().getTime(),
            "Project1",
            "C@live.com",
            PROJECT_STATUS_PENDING,
            Timestamp.now(),
            taskLst,
            Timestamp.now(),
        )



    }




