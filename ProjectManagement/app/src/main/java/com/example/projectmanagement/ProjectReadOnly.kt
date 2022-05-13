package com.example.projectmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.model.OneProjectDetailsViewModel
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.table.project.TaskTableViewAdaptor
import com.example.projectmanagement.utils.INTENT_FROM_READ_PROJECT
import com.example.projectmanagement.utils.TableHeader
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.codecrafters.tableview.TableView

class ProjectReadOnly : AppCompatActivity() {
    private lateinit var projectName : EditText
    private lateinit var prjDeadline : EditText
    private lateinit var createdBy : EditText
    private lateinit var tableView : TableView<com.example.projectmanagement.model.TaskDetails>
    private var database = Firebase.firestore
    private lateinit var projectDetails : ProjectDetails
    private lateinit var oneProjectDetailsViewModel: OneProjectDetailsViewModel
    private lateinit var context: Context
    private lateinit var okayBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_read_only)
        projectName = findViewById(R.id.edtRdProjectName)
        prjDeadline = findViewById(R.id.edtDeadline)
        createdBy = findViewById(R.id.edtCreatedBy)
        context = this
        okayBtn = findViewById(R.id.btnBack)
        oneProjectDetailsViewModel = ViewModelProvider(this).get(OneProjectDetailsViewModel::class.java)
        tableView = findViewById(R.id.tableReadOnlyTaskView)
        tableView.columnCount=3
        intent.getStringExtra("projectId")?.let { getOneProjectDetails(it.toLong()) }

        okayBtn.setOnClickListener{
            navigateToProjectListing()
        }




    }

    private fun navigateToProjectListing() {

        val intent = Intent(this@ProjectReadOnly, ListingProject::class.java)
        println(createdBy.text.toString())
        intent.putExtra("email", createdBy.text.toString())
        intent.putExtra("code", INTENT_FROM_READ_PROJECT)
        startActivity(intent)
        finish()
    }

    private fun getOneProjectDetails(projectId : Long) {
        oneProjectDetailsViewModel.getOneProjectDetails(object : FirestoreCallback {
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                TODO("Not yet implemented")
            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }

            override fun onOneProjectDetailsCallback(projectDetails: ProjectDetails) {
                var taskLst = projectDetails.taskLst
                val adapter = TaskTableViewAdaptor(context,taskLst,tableView)
                tableView.dataAdapter = adapter
                tableView.headerAdapter = TableHeader.getTaskTableHeader(context, "")
                projectName.setText(projectDetails.projectName)
                prjDeadline.setText(projectDetails.projectDeadline?.toDate().toString())
                createdBy.setText(projectDetails.projectCreatedBy.toString())
                projectName.isEnabled=false
                createdBy.isEnabled=false
                prjDeadline.isEnabled=false
            }

            override fun onTeamMemberProjectCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }
        }, projectId = projectId)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heading_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuLogout-> Intent(this@ProjectReadOnly, MainActivity::class.java).also {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)

            }
        }
        return super.onOptionsItemSelected(item)
    }

}