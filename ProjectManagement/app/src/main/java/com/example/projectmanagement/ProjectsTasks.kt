package com.example.projectmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.model.TaskDetails
import com.google.firebase.Timestamp
import com.google.gson.Gson
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.TeamMemberEmailViewModel
import com.example.projectmanagement.utils.INTENT_FROM_TASK_LIST

private lateinit var buttonSubmitTask: Button
private lateinit var edtTaskName : EditText
private lateinit var datePicker: DatePicker
private lateinit var taskStatus : TextView
private lateinit var teamMemberEmailViewModel: TeamMemberEmailViewModel
private lateinit var listOfTeamMembers : MutableList<String>
private lateinit var context: Context
private lateinit var assignedUser : Spinner
private lateinit var assignedToUser : String
private var task: TaskDetails = TaskDetails()
class ProjectsTasks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProjectManagement)
        setContentView(R.layout.activity_projects_tasks)
        edtTaskName = findViewById(R.id.edtTaskName)
        //assignedUser = findViewById(R.id.edtAssignedUser)
        datePicker = findViewById(R.id.dteTaskDeadline)
        taskStatus = findViewById(R.id.txtAssigned)
        buttonSubmitTask= findViewById<Button>(R.id.btnSubmitTask)
        context = this
        teamMemberEmailViewModel = ViewModelProvider(this).get(TeamMemberEmailViewModel::class.java)
        assignedUser = findViewById(R.id.spinnerAssignedUser)
        val intent = intent
        getTeamMemberEmails("")
        println(intent.getStringExtra("json"))
        println(intent.getStringExtra("code"))
        buttonSubmitTask.setOnClickListener{
            task.taskDeadline = Timestamp.now()
            task.taskId = Timestamp.now().nanoseconds.toLong()
            task.taskName = edtTaskName.text.toString()
            task.taskStatus = taskStatus.text.toString()
            var taskList  = mutableListOf<TaskDetails>()
            taskList.add(task)
            val projectDetails = Gson().fromJson<ProjectDetails>(intent.getStringExtra("json"), ProjectDetails::class.java)
            if(projectDetails.taskLst?.size == 0){
                projectDetails.taskLst=taskList
            }else{
                var taskLst = projectDetails.taskLst
                taskLst?.add(task)
                projectDetails.taskLst = taskLst
            }
            val prjLstIntent = Intent(this,com.example.projectmanagement.ProjectDetails::class.java)
            prjLstIntent.putExtra("json",Gson().toJson(projectDetails))
            prjLstIntent.putExtra("code", INTENT_FROM_TASK_LIST)
            startActivity(prjLstIntent)
            finish()

        }

    }

    private fun getTeamMemberEmails(role: String) {
        teamMemberEmailViewModel.getTeamMemberDetails(object : FirestoreCallback{
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                println("role")
                listOfTeamMembers = teamMemberLst
                val adapter = ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,
                    listOfTeamMembers)
                assignedUser.adapter = adapter
                println(listOfTeamMembers)

            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }

            override fun onOneProjectDetailsCallback(projectDetails: ProjectDetails) {
                TODO("Not yet implemented")
            }
        }, role = "Team Member")
    }

    private fun getEmailSelected() {
        assignedUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val assignedUser = parent!!.getItemAtPosition(position)
                assignedToUser = assignedUser.toString()
                println(assignedToUser)


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

}