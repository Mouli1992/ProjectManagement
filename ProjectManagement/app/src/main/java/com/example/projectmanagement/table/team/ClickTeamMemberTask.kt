package com.example.projectmanagement.table.team

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.projectmanagement.*
import com.example.projectmanagement.model.TaskDetails
import de.codecrafters.tableview.listeners.TableDataClickListener
import com.example.projectmanagement.db.FirestoreRepository
import com.example.projectmanagement.model.OneProjectDetailsViewModel
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.utils.PROJECT_STATUS_COMPLETE
import com.example.projectmanagement.utils.TASK_STATUS_CLOSED
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic2"

class ClickTeamMemberTask (private val context: Context, private val projectId: Long, private val name : String , private val role : String , private val email : String, private val profileImage : String):
    TableDataClickListener<TaskDetails> {
    private val msgTitle = "Task Update Notification"
    private val message= "Task Updated"
    private lateinit var oneProjectDetailsViewModel: OneProjectDetailsViewModel
    private val database = Firebase.firestore
    private lateinit var projectDetails : MutableList<ProjectDetails>
    override fun onDataClicked(rowIndex: Int, clickedData: TaskDetails?) {
        val taskUpdateDialog = AlertDialog.Builder(context)
            .setTitle("Task Update")
            .setMessage("Are you sure you want to update the status of the task")
            .setIcon(R.drawable.ic_dialog_image)
            .setPositiveButton("yes") {_,_ ->
                if(clickedData?.taskStatus.equals("Assigned")) {
                    updateTaskDetails(projectId, clickedData?.taskId)
                    if(msgTitle.isNotEmpty() && message.isNotEmpty()) {
                        PushNotification(
                            NotificationData(msgTitle, message),
                            TOPIC
                        ).also {
                            sendNotification(it)
                        }
                    }
                    val taskIntent = Intent(context, TeamMemberTaskList::class.java)
                    println("Project ID $projectId")
                    taskIntent.putExtra("name", name)
                    taskIntent.putExtra("email", email)
                    taskIntent.putExtra("role", role)
                    taskIntent.putExtra("profileImage", profileImage)
                    taskIntent.putExtra("projectId", projectId.toString())
                    context.startActivity(taskIntent)
                    Toast.makeText(context, "Task Status Updated", Toast.LENGTH_SHORT)
                        .show()
                }else{
                    Toast.makeText(context, "Project status already updated", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("No"){_,_ ->

                Toast.makeText(context,"The task status wasnt updated",Toast.LENGTH_SHORT).show()
            }
        val alertDialog: AlertDialog = taskUpdateDialog.create()
        alertDialog.show();
    }

    private fun updateTaskDetails(projectId: Long, taskId: Long?) {
        database.collection("projectDetails").whereEqualTo("projectId", projectId ).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   var projectDetails1 = task.result.toObjects(ProjectDetails::class.java)
                    println(projectDetails1)
                    if (taskId != null) {
                        updateTaskDetailsInDB(projectDetails1[0],taskId)
                    }
                } else {
                    Log.e(FirestoreRepository.TAG, "Error in getManagerProjectDetails Details :", task.exception)
                }


    }


}

    private fun updateTaskDetailsInDB(projectDetails: ProjectDetails?, taskId : Long) {
        var taskLst = projectDetails?.taskLst
        var newTaskLst = mutableListOf<TaskDetails>()
        var flag : Boolean = false
        if (taskLst != null) {
            for (eachTask in taskLst){
                if (eachTask.taskId == taskId){
                    var newTask = eachTask
                    newTask.taskStatus = TASK_STATUS_CLOSED
                    newTaskLst.add(newTask)
                }else{
                    if(eachTask.taskStatus.equals("Assigned")){
                        flag = true
                    }
                    newTaskLst.add(eachTask)
                }

            }
        }
        var projectDetailsNew = projectDetails
        projectDetailsNew?.taskLst = newTaskLst
        if (flag){
            projectDetailsNew?.projectStatus = PROJECT_STATUS_COMPLETE
        }

        if (projectDetailsNew != null) {
            database.collection("projectDetails").document(projectDetails?.projectId.toString()).set(projectDetailsNew).addOnSuccessListener {
                println("Completed")
            }
        }

    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful) {
                Log.d(com.example.projectmanagement.ProjectDetails.TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(com.example.projectmanagement.ProjectDetails.TAG, response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.e(com.example.projectmanagement.ProjectDetails.TAG, e.toString())
        }
    }

    }
