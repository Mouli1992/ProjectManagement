package com.example.projectmanagement

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import java.text.SimpleDateFormat
import java.util.*

private lateinit var buttonSubmitTask: Button
private lateinit var edtTaskName : EditText
private lateinit var taskDeadline: EditText
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
        taskDeadline = findViewById(R.id.dteTaskDeadline)
        taskDeadline.setOnClickListener{
            openDatePickerDialog()
        }
        taskStatus = findViewById(R.id.txtAssigned)
        buttonSubmitTask= findViewById<Button>(R.id.btnSubmitTask)
        context = this
        teamMemberEmailViewModel = ViewModelProvider(this).get(TeamMemberEmailViewModel::class.java)
        assignedUser = findViewById(R.id.spinnerAssignedUser)
        getEmailSelected()
        val intent = intent
        getTeamMemberEmails("")
        println(intent.getStringExtra("json"))
        println(intent.getStringExtra("code"))
        buttonSubmitTask.setOnClickListener{
            val sdf : SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
            println("Task Deadline${taskDeadline.text.toString()}")
            val date: Date = sdf.parse(taskDeadline.text.toString())
            println(date.toString())
            task.taskDeadline = Timestamp(date)
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

    private fun openDatePickerDialog() {
        // Get Current Date
        val cal: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this@ProjectsTasks,
            { view, year, monthOfYear, dayOfMonth ->
                val selectedDate: String =
                    dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                taskDeadline.setText(selectedDate)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = cal.timeInMillis
        datePickerDialog.show()
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

            override fun onTeamMemberProjectCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }
        }, role = "Team Member")
    }

    private fun getEmailSelected() {
        assignedUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val assignedUser = parent!!.getItemAtPosition(position)
                assignedToUser = assignedUser.toString()
                task.assignedTo=assignedToUser
                println("AssignedUser$assignedUser")
                println("assignedToUser$assignedToUser")


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heading_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuLogout-> Intent(this@ProjectsTasks, MainActivity::class.java).also {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)

            }
        }
        return super.onOptionsItemSelected(item)
    }

}