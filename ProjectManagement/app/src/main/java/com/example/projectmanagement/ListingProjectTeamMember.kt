package com.example.projectmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.TeamMemberProjectDetailsViewModel
import com.example.projectmanagement.table.project.ProjectClickListener
import com.example.projectmanagement.table.project.TaskClickListener
import com.example.projectmanagement.table.project.TeamMemberTableAdaptor
import com.example.projectmanagement.utils.TableHeader
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.codecrafters.tableview.TableView

class ListingProjectTeamMember : AppCompatActivity() {

    private lateinit var tableView : TableView<ProjectDetails>
    private var database = Firebase.firestore
    private lateinit var projectDetails : ProjectDetails
    private lateinit var context: Context
    private lateinit var teamMemberProjectViewModel: TeamMemberProjectDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_project_team_member)
        tableView = findViewById(R.id.tableViewProjectListingTeamMember)
        tableView.columnCount=3
        teamMemberProjectViewModel = ViewModelProvider(this).get(TeamMemberProjectDetailsViewModel::class.java)
        projectDetails = ProjectDetails()
        context = this
        intent.getStringExtra("email")?.let { getTeamMemberProjectDetails(it) }
        tableView.addDataClickListener(TaskClickListener(this))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heading_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuLogout-> Intent(this@ListingProjectTeamMember, MainActivity::class.java).also {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTeamMemberProjectDetails(email : String) {

        teamMemberProjectViewModel.getProjectDetails(object : FirestoreCallback {
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                TODO("Not yet implemented")
            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }

            override fun onOneProjectDetailsCallback(projectDetails: ProjectDetails) {
                TODO("Not yet implemented")
            }

            override fun onTeamMemberProjectCallback(projectDetails: MutableList<ProjectDetails>) {
                val adapter = TeamMemberTableAdaptor(context,projectDetails,tableView)
                tableView.dataAdapter = adapter
                tableView.headerAdapter = TableHeader.getProjectTableHeader(context, "")
            }

        }, email = email)
    }
}