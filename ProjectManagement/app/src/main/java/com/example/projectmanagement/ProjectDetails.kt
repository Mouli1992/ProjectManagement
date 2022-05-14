package com.example.projectmanagement

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.core.view.isVisible
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.TaskDetails
import com.example.projectmanagement.table.TaskTableViewAdaptor
import com.example.projectmanagement.utils.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import de.codecrafters.tableview.TableView
import java.text.SimpleDateFormat
import java.util.*

lateinit var submitPB:Button
//private lateinit var taskRecyclerView: TableView<TaskDetails>
private lateinit var tableView: TableView<com.example.projectmanagement.model.TaskDetails>
private lateinit var txtNoTasks: TextView
private lateinit var projectName: EditText
private lateinit var prjDeadline: EditText
private lateinit var createdBy: EditText
private lateinit var projectDetails : ProjectDetails
private lateinit var taskList: MutableList<TaskDetails>
private var database = Firebase.firestore
private lateinit var datePicker : DatePicker
private lateinit var context: Context
private lateinit var buttonAddProjectTasks : Button

class ProjectDetails : AppCompatActivity() {
    companion object{
        const val TAG = "ProjectDetails"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProjectManagement)
        setContentView(R.layout.activity_project_details)
        buttonAddProjectTasks = findViewById<Button>(R.id.btnAddTasks)
        //taskRecyclerView = findViewById(R.id.rvTasks)
        tableView = findViewById(R.id.rvTasks)
        txtNoTasks= findViewById(R.id.txtNoTasks)
        projectName = findViewById(R.id.edtProjectName)
        prjDeadline = findViewById(R.id.dteProjectDeadline)
        createdBy = findViewById(R.id.edtCreatedBy)
        context = this
        prjDeadline.setOnClickListener{
            openDatePickerDialog()

        }
        val intent = intent
        if(intent.getStringExtra("code").equals(INTENT_FROM_PROJECT_LIST)) {
            createdBy.setText(intent.getStringExtra("email"))
            createdBy.isEnabled=false
            txtNoTasks.isVisible=true
            tableView.isVisible=false
            //taskRecyclerView.isVisible=false

        }else if (intent.getStringExtra("code").equals(INTENT_FROM_TASK_LIST)){
            projectDetails = Gson().fromJson<ProjectDetails>(intent.getStringExtra("json"), ProjectDetails::class.java)
            println("Email "+projectDetails?.projectCreatedBy.toString())
            createdBy.setText(projectDetails?.projectCreatedBy)
            createdBy.isEnabled=false
            projectName.setText(projectDetails?.projectName)
            projectName.isEnabled=false
            prjDeadline.setText(projectDetails.projectDeadline?.toDate().toString())
            prjDeadline.isEnabled=false
            //prjDeadline.setText(projectDetails.projectDeadline?.nanoseconds.toString())
            tableView.isVisible=true
            tableView.columnCount=3
            taskList = projectDetails.taskLst!!
            println(taskList)
            val adapter = TaskTableViewAdaptor(context,taskList,tableView)
            tableView.dataAdapter = adapter
            tableView.headerAdapter = TableHeader.getTaskTableHeader(context, "")
            tableView.isVisible=true
//            val adapter1 = TaskListAdapter(taskList)
//            taskRecyclerView.adapter=adapter1
//            taskRecyclerView.layoutManager= LinearLayoutManager(this)
            txtNoTasks.isVisible = false
//            taskRecyclerView.isVisible = true


        }else if (intent.getStringExtra("code").equals(INTENT_FROM_PRJ_LST_FOR_PRJ)){


        }


        buttonAddProjectTasks.setOnClickListener {
            if(intent.getStringExtra("code").equals(INTENT_FROM_PROJECT_LIST)){
                projectDetails = ProjectDetails()
                val sdf : SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
                println("Project Deadline${prjDeadline.text.toString()}")
                val date: Date = sdf.parse(prjDeadline.text.toString())
                println(date.toString())
                projectDetails.projectDeadline = Timestamp(date)
                projectDetails.projectName = projectName.text.toString()
                projectDetails.projectCreatedBy = createdBy.text.toString()
                projectDetails.createdAt = Timestamp.now()
                projectDetails.projectId= Timestamp.now().nanoseconds.toLong()
                projectDetails.projectStatus = PROJECT_STATUS_PENDING

                println(projectDetails)
            }

            val taskLstIntent = Intent(this@ProjectDetails, ProjectsTasks::class.java)
            taskLstIntent.putExtra("json", Gson().toJson(projectDetails))
            taskLstIntent.putExtra("code", INTENT_FROM_PROJECT_DETAILS)
            startActivity(taskLstIntent)
        }


        submitPB = findViewById(R.id.btnSubmitProject)
        submitPB.setOnClickListener {
            projectDetails.teamLst = getTeamLst(projectDetails)
            database.collection("projectDetails").document(projectDetails.projectId.toString()).
            set(projectDetails).addOnCompleteListener { projectTaskDetailsTask ->
                if(projectTaskDetailsTask.isSuccessful) {
                    Toast.makeText(this, "Project Details Added Successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Log.e(TAG, "Exception with Project Data Insertion")
                    Toast.makeText(this, "Project Details Insertion Failed", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }

            }
            val projectListingIntent = Intent(this, ListingProject::class.java)
            projectListingIntent.putExtra("email",projectDetails.projectCreatedBy)
            finish()
            startActivity(projectListingIntent)

        }
    }

    private fun getTeamLst(projectDetails: ProjectDetails) : MutableList<String>? {
        var teamLst = mutableListOf<String>()
        for (task in projectDetails.taskLst!!){
            task.assignedTo?.let { teamLst.add(it) }
        }

        return teamLst


    }

    private fun openDatePickerDialog() {
        // Get Current Date
        val cal: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this@ProjectDetails,
            { view, year, monthOfYear, dayOfMonth ->
                val selectedDate: String =
                    dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                prjDeadline.setText(selectedDate)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = cal.timeInMillis
        datePickerDialog.show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heading_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuLogout-> Intent(this@ProjectDetails, MainActivity::class.java).also {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)

            }

            R.id.menuHome-> Intent(this@ProjectDetails, ListingProject::class.java).also {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
            }
            return super.onOptionsItemSelected(item)
        }



    }
