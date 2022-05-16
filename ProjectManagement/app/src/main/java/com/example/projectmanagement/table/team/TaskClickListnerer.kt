package com.example.projectmanagement.table.team

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.projectmanagement.TeamMemberTaskList
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.utils.INTENT_FROM_PRJ_LST_FOR_PRJ
import de.codecrafters.tableview.listeners.TableDataClickListener

class TaskClickListener(private val context: Context, private val email: String,private val role : String,private val name : String, private val profilePic : String): TableDataClickListener<ProjectDetails> {

    companion object{
        private const val TAG = "ListingProject"
        private const val PROJECT_LISTING_ACTING = 240
    }
    var projectId:Long = 0
    override fun onDataClicked(rowIndex: Int, clickedData: ProjectDetails?){
        clickedData!!.projectName?.let { Log.i("TestTask", it)
            projectId = clickedData!!.projectId}
        var intent = Intent(context, TeamMemberTaskList::class.java)
        println("projectID $projectId")
        println("project Name $name")
        println("project Role $role")
        println("project email $email")
        println("project profile Iamge $profilePic")
        intent.putExtra("code", INTENT_FROM_PRJ_LST_FOR_PRJ)
        intent.putExtra("projectId",projectId.toString() )
        intent.putExtra("email",email)
        intent.putExtra("role",role)
        intent.putExtra("name",name)
        intent.putExtra("profileImage",profilePic)
        context.startActivity(intent)
        Log.i("Test",projectId.toString())

    }
}

