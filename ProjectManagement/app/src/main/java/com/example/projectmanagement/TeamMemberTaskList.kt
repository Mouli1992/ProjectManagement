package com.example.projectmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.model.OneProjectDetailsViewModel
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.TaskDetails
import com.example.projectmanagement.model.TeamMemberProjectDetailsViewModel
import com.example.projectmanagement.table.project.TaskTableViewAdaptor
import com.example.projectmanagement.utils.TableHeader
import de.codecrafters.tableview.TableView

class TeamMemberTaskList : AppCompatActivity() {
    private lateinit var managerName : TextView
    private lateinit var projectStatus : TextView
    private lateinit var teamMemberTaskList: TextView
    private lateinit var taskTableView : TableView<TaskDetails>
    private lateinit var projectDetailsViewModel : OneProjectDetailsViewModel
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_member_task_list)

        managerName = findViewById(R.id.txtAssignedManagertmtl)
        projectStatus = findViewById(R.id.txtProjectStatustmtl)
        teamMemberTaskList = findViewById(R.id.txtMembersNametmtl)
        taskTableView = findViewById(R.id.teamMemberTv)
        projectDetailsViewModel = ViewModelProvider(this).get(OneProjectDetailsViewModel::class.java)
        context = this

        intent.getStringExtra("projectId")?.let { getProjectDetails(it.toLong()) }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heading_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuLogout-> Intent(this@TeamMemberTaskList, MainActivity::class.java).also {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getProjectDetails(projectId : Long) {
        projectDetailsViewModel.getOneProjectDetails(object : FirestoreCallback {
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                TODO("Not yet implemented")
            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }

            override fun onOneProjectDetailsCallback(projectDetails: ProjectDetails) {
                taskTableView.columnCount=3
                var taskLst = projectDetails.taskLst
                val adapter = TaskTableViewAdaptor(context,taskLst,taskTableView)
                taskTableView.dataAdapter = adapter
                taskTableView.headerAdapter = TableHeader.getTaskTableHeader(context, "")
                managerName.setText("Manager Name :"+projectDetails.projectName.toString())
                projectStatus.setText("Project Status"+projectDetails.projectStatus.toString())
                teamMemberTaskList.setText(projectDetails.teamLst.toString())
                managerName.isEnabled=false
                projectStatus.isEnabled=false
                teamMemberTaskList.isEnabled=false
            }

            override fun onTeamMemberProjectCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }
        }, projectId = projectId)

    }
}