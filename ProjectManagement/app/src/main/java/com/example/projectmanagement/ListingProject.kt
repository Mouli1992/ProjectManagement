package com.example.projectmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button as Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.db.FirestoreRepository
import com.example.projectmanagement.model.*
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.table.project.ProjectClickListener
import com.example.projectmanagement.table.project.ProjectTableDataAdapter
import com.example.projectmanagement.utils.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.codecrafters.tableview.TableView
import java.util.*

class ListingProject : AppCompatActivity()  {
    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var txtNo: TextView
    private val database = Firebase.firestore
    private lateinit var buttonAddProject : Button
    private lateinit var listOfTeamMembers : MutableList<String>
    private lateinit var teamMemberEmailViewModel: TeamMemberEmailViewModel
    private lateinit var managerProjectDetailsViewModel : ProjectDetailsViewModel
    private lateinit var tableView: TableView<ProjectDetails>
    private lateinit var context : Context
    companion object{
        private const val TAG = "ListingProject"
        private const val CREATE_REQUEST_CODE = 248

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProjectManagement)
        setContentView(R.layout.activity_listing_project)

        //projectRecyclerView = findViewById(R.id.rvProjectShown)
        txtNo = findViewById(R.id.txtNoProjects)
        listOfTeamMembers = mutableListOf()
        val todoList = mutableListOf<ProjectListData>(
            ProjectListData("p1", "pending"),
            ProjectListData("p2", "pending"),
        )

        tableView = findViewById(R.id.tableView)
        tableView.columnCount=3
        context = this

//        projectRecyclerView.addOnItemTouchListener(
//            RecyclerView.OnItemTouchListener(RecyclerItemClickListenr(this, projectRecyclerView, object : RecyclerItemClickListenr.OnItemClickListener {
//
//                override fun onItemClick(view: View, position: Int) {
//                    //do your work here..
//                }
//                override fun onItemLongClick(view: View?, position: Int) {
//                    TODO("do nothing")
//                }
//            }))
//        )
        teamMemberEmailViewModel = ViewModelProvider(this).get(TeamMemberEmailViewModel::class.java)
        getTeamMemberEmails(USER_ROLE_TEAM_MEMBER)
        managerProjectDetailsViewModel = ViewModelProvider(this).get(ProjectDetailsViewModel::class.java)
        val intent = intent
        getProjectDetailsOfManager(intent.getStringExtra("email")!!)
        buttonAddProject = findViewById<Button>(R.id.btnAddProject)

        tableView.addDataClickListener(ProjectClickListener(this))
        buttonAddProject.setOnClickListener{

            val prevIntent = intent;
            val intent = Intent(this@ListingProject, com.example.projectmanagement.ProjectDetails::class.java)
            intent.putExtra("name",prevIntent.getStringExtra("name") )
            intent.putExtra("email", prevIntent.getStringExtra("email"))
            intent.putExtra("code", INTENT_FROM_PROJECT_LIST)
            startActivity(intent)
            finish()

        }




    }

//    private fun getProjectDetailsOfManager(email : String) {
//        managerProjectDetailsViewModel.getProjectDetails(object : FirestoreCallback{
//            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
//                val adapter = ProjectListAdapter(projectDetails)
//                projectRecyclerView.adapter = adapter
//                projectRecyclerView.layoutManager = LinearLayoutManager(this@ListingProject)
//                if (projectDetails.isNotEmpty()) {
//                    txtNo.isVisible = false
//                    projectRecyclerView.isVisible = true;
//                } else {
//                    txtNo.isVisible = true;
//                    projectRecyclerView.isVisible = false;
//                }
//            }
//
//
//        }, email = email)
//
//    }

    private fun getProjectDetailsOfManager(email : String) {
        managerProjectDetailsViewModel.getProjectDetails(object : FirestoreCallback {
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                TODO("Not yet implemented")
            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                val adapter = ProjectTableDataAdapter(context,projectDetails,tableView)
                tableView.dataAdapter = adapter
                tableView.headerAdapter = TableHeader.getProjectTableHeader(context, "")


                if (projectDetails.isNotEmpty()) {
                    txtNo.isVisible = false
                    tableView.isVisible = true;
                } else {
                    txtNo.isVisible = true;
                    tableView.isVisible = false;
                }

            }
        }, email = email)

    }

    private fun getTeamMemberEmails(role: String) {
       teamMemberEmailViewModel.getTeamMemberDetails(object : FirestoreCallback{
           override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
               listOfTeamMembers = teamMemberLst
               println(listOfTeamMembers)


           }

           override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
               TODO("Not yet implemented")
           }
       }, role = "Team Member")
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




