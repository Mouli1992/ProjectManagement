package com.example.projectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button as Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.model.*
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.utils.PROJECT_STATUS_PENDING
import com.example.projectmanagement.utils.TASK_STATUS_ONGOING
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ListingProject : AppCompatActivity()  {
    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var txtNo: TextView
    private val database = Firebase.firestore
    private lateinit var buttonAddProject : Button
    private lateinit var listOfTeamMembers : MutableList<String>
    private lateinit var teamMemberEmailViewModel: TeamMemberEmailViewModel
    private lateinit var managerProjectDetailsViewModel : ProjectDetailsViewModel
    companion object{
        private const val TAG = "ListingProject"
        private const val CREATE_REQUEST_CODE = 248

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_project)

        projectRecyclerView = findViewById(R.id.rvProjectShown)
        txtNo = findViewById(R.id.txtNoProjects)
        listOfTeamMembers = mutableListOf()
        val todoList = mutableListOf<ProjectListData>(
            ProjectListData("p1", "pending"),
            ProjectListData("p2", "pending"),
        )

        teamMemberEmailViewModel = ViewModelProvider(this).get(TeamMemberEmailViewModel::class.java)
        getTeamMemberEmails()
        managerProjectDetailsViewModel = ViewModelProvider(this).get(ProjectDetailsViewModel::class.java)
        getProjectDetailsOfManager()

        buttonAddProject = findViewById<Button>(R.id.btnAddProject)

        buttonAddProject.setOnClickListener{
            val projectTaskDetails : ProjectDetails = createDummyProjectDetails()
            database.collection("projectDetails").document(projectTaskDetails.projectId.toString()).
            set(projectTaskDetails).addOnCompleteListener { projectTaskDetailsTask ->
                if(projectTaskDetailsTask.isSuccessful) {
                    Toast.makeText(this, "Project Details Added Successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Log.e(TAG, "Exception with User Registration")
                    Toast.makeText(this, "Project Details Insertion Failed", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }

            }

        }




    }

    private fun getProjectDetailsOfManager() {
        managerProjectDetailsViewModel.getProjectDetails(object : FirestoreCallback{
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                TODO("Not yet implemented")
            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                val adapter = ProjectListAdapter(projectDetails)
                projectRecyclerView.adapter = adapter
                projectRecyclerView.layoutManager = LinearLayoutManager(this@ListingProject)
                if (projectDetails.isNotEmpty()) {
                    txtNo.isVisible = false
                    projectRecyclerView.isVisible = true;
                } else {
                    txtNo.isVisible = true;
                    projectRecyclerView.isVisible = false;
                }
            }


        })

    }

    private fun getTeamMemberEmails() {
       teamMemberEmailViewModel.getTeamMemberDetails(object : FirestoreCallback{
           override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
               println(teamMemberLst)
           }

           override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
               TODO("Not yet implemented")
           }
       })
    }

            //Commented by Chandra to test project task Additon
//            val intent = Intent(this, ProjectDetails:: class.java).also {
//                startActivity(it)




    private fun createDummyProjectDetails(): ProjectDetails {
        var taskLst = mutableListOf<TaskDetails>()
        var taskDetails = TaskDetails(
            Integer.toUnsignedLong(Timestamp.now().nanoseconds),
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


    }




