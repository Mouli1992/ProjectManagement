package com.example.projectmanagement

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.TaskDetails
import com.example.projectmanagement.utils.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.*

lateinit var submitPB:Button
private lateinit var taskRecyclerView: RecyclerView
private lateinit var txtNoTasks: TextView
private lateinit var projectName: EditText
private lateinit var prjDeadline: DatePicker
private lateinit var createdBy: EditText
private lateinit var projectDetails : ProjectDetails
private lateinit var taskList: MutableList<TaskDetails>
private var database = Firebase.firestore
private lateinit var datePicker : DatePicker

class ProjectDetails : AppCompatActivity() {
    companion object{
        const val TAG = "ProjectDetails"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProjectManagement)
        setContentView(R.layout.activity_project_details)
        val buttonAddProjectTasks = findViewById<Button>(R.id.btnAddTasks)
        taskRecyclerView = findViewById(R.id.rvTasks)
        txtNoTasks= findViewById(R.id.txtNoTasks)
        projectName = findViewById(R.id.edtProjectName)
        prjDeadline = findViewById(R.id.dteProjectDeadline)
        createdBy = findViewById(R.id.edtCreatedBy)

        val mcurrentTime = Calendar.getInstance()
        val year = mcurrentTime.get(Calendar.YEAR)
        val month = mcurrentTime.get(Calendar.MONTH)
        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)


//        val datePicker = DatePickerDialog(this,
//            { view, year, month, dayOfMonth ->
//                prjDeadline.setText(String.format("%d / %d / %d", dayOfMonth, month + 1, year)) }, year, month, day);
//
//        prjDeadline.setOnClickListener()

//        val taskList= mutableListOf<TaskListData>(
//           TaskListData("t1","pending"),
//            TaskListData("t2","pending"),
//        )

//        val projectList= mutableListOf<ProjectListData>(
//           ProjectListData("t1","pending"),
//            ProjectListData("t2","pending"),
//       )
        val intent = intent
        if(intent.getStringExtra("code").equals(INTENT_FROM_PROJECT_LIST)) {
            createdBy.setText(intent.getStringExtra("email"))
            createdBy.isEnabled=false
            txtNoTasks.isVisible=true
            taskRecyclerView.isVisible=false
            println()
        }else if (intent.getStringExtra("code").equals(INTENT_FROM_TASK_LIST)){
            projectDetails = Gson().fromJson<ProjectDetails>(intent.getStringExtra("json"), ProjectDetails::class.java)
            println("Email "+projectDetails?.projectCreatedBy.toString())
            createdBy.setText(projectDetails?.projectCreatedBy)
            projectName.setText(projectDetails?.projectName)
            //prjDeadline.setText(projectDetails.projectDeadline?.nanoseconds.toString())

            taskList = projectDetails.taskLst!!
            val adapter1 = TaskListAdapter(taskList)
            taskRecyclerView.adapter=adapter1
            taskRecyclerView.layoutManager= LinearLayoutManager(this)
            txtNoTasks.isVisible = false
            taskRecyclerView.isVisible = true


        }else if (intent.getStringExtra("code").equals(INTENT_FROM_PRJ_LST_FOR_PRJ)){


        }


        buttonAddProjectTasks.setOnClickListener {
            if(intent.getStringExtra("code").equals(INTENT_FROM_PROJECT_LIST)){
                projectDetails = ProjectDetails()
                projectDetails.projectDeadline = Timestamp.now()
                projectDetails.projectName = projectName.text.toString()
                projectDetails.projectCreatedBy = createdBy.text.toString()
                projectDetails.createdAt = Timestamp.now()
                projectDetails.projectId= Timestamp.now().nanoseconds.toLong()
                projectDetails.projectStatus = PROJECT_STATUS_PENDING
            }

            val taskLstIntent = Intent(this@ProjectDetails, ProjectsTasks::class.java)
            taskLstIntent.putExtra("json", Gson().toJson(projectDetails))
            taskLstIntent.putExtra("code", INTENT_FROM_PROJECT_DETAILS)
            startActivity(taskLstIntent)
            }

            submitPB = findViewById(R.id.btnSubmitProject)
            submitPB.setOnClickListener {

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



    }
