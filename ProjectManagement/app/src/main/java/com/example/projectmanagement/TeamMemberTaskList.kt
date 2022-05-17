package com.example.projectmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.model.*
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.table.team.ClickTeamMemberTask
import com.example.projectmanagement.table.team.TaskTableViewAdaptor
import com.example.projectmanagement.utils.TableHeader
import de.codecrafters.tableview.TableView

class TeamMemberTaskList : AppCompatActivity() {
    private lateinit var managerName : EditText
    private lateinit var projectStatus : EditText
    private lateinit var taskTableView : TableView<TaskDetails>
    private lateinit var projectDetailsViewModel : OneProjectDetailsViewModel
    private lateinit var context: Context
    private lateinit var teamLstTv : TableView<TeamDetails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_member_task_list)

        managerName = findViewById(R.id.edtProjectManagerName)
        projectStatus = findViewById(R.id.edtProjectStatus)
        teamLstTv = findViewById(R.id.teamLstTv)
        taskTableView = findViewById(R.id.teamMemberTv)
        projectDetailsViewModel = ViewModelProvider(this).get(OneProjectDetailsViewModel::class.java)
        context = this
       // var projectId = intent.getStringExtra("projectId")
        println("Name ${intent.getStringExtra("name")}")
        println("role ${intent.getStringExtra("role")}")
        println("profileImage ${intent.getStringExtra("profileImage")}")
        println("email ${intent.getStringExtra("email")}")
        intent.getStringExtra("projectId")?.let { getProjectDetails(it) }
        taskTableView.addDataClickListener(intent.getStringExtra("projectId")?.let {
            ClickTeamMemberTask(this, it.toLong() , intent.getStringExtra("name")!!, intent.getStringExtra("role")!!, intent.getStringExtra("email")!!, intent.getStringExtra("profileImage")!!)
        })


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

            R.id.menuHome-> Intent(this@TeamMemberTaskList, ListingProjectTeamMember::class.java).also {
                println(intent.getStringExtra("profileImage"))
                it.putExtra("email",intent.getStringExtra("email"))
                it.putExtra("name",intent.getStringExtra("name"))
                it.putExtra("role",intent.getStringExtra("role"))
                it.putExtra("profileImage",intent.getStringExtra("profileImage"))
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getProjectDetails(projectId : String) {
        projectDetailsViewModel.getOneProjectDetails(object : FirestoreCallback {
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                TODO("Not yet implemented")
            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }

            override fun onOneProjectDetailsCallback(projectDetails: ProjectDetails) {
                taskTableView.columnCount=3
                var taskLst = mutableListOf<TaskDetails>()
                    for (eachTask in projectDetails.taskLst!!){
                        if(eachTask.assignedTo.contentEquals(intent.getStringExtra("email")))
                            taskLst.add(eachTask)

                    }

                val adapter = TaskTableViewAdaptor(context,taskLst,taskTableView)
                taskTableView.dataAdapter = adapter
                taskTableView.headerAdapter = TableHeader.getTaskTableHeader(context, "")
                teamLstTv.columnCount=1
                teamLstTv.headerAdapter = TableHeader.getTeamLstTableHeader(context,"")
                val teamDetails = getTeamDetails(projectDetails.teamLst)
                val teamLstAdaptor = TeamLstTableAdaptor(context,teamDetails,teamLstTv)
                teamLstTv.dataAdapter = teamLstAdaptor
                //teamLstTv.dataAdapter = SimpleTableDataAdapter(MutableList(projectDetails.teamLst))
                managerName.setText(projectDetails.projectName.toString())
                projectStatus.setText(projectDetails.projectStatus.toString())
                managerName.isEnabled=false
                projectStatus.isEnabled=false
            }

            override fun onTeamMemberProjectCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }
        }, projectId = projectId.toLong())

    }

    private fun getTeamDetails(teamLst: MutableList<String>?): MutableList<TeamDetails> {
        val teamDetailsLst :MutableList<TeamDetails> = mutableListOf()
        if (teamLst != null) {
            for(team in teamLst){
                teamDetailsLst.add(TeamDetails(team))
            }
        }
        println("teamDetailsLst :: $teamDetailsLst")
        return teamDetailsLst
    }
}