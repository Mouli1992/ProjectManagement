package com.example.projectmanagement.table.project

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.projectmanagement.ProjectReadOnly
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.utils.INETNT_FROM_TEAM_PRJ_LIST
import de.codecrafters.tableview.listeners.TableDataClickListener


class ProjectClickListener (private val context: Context, val name : String, val role : String, val email : String, val profileImage : String):TableDataClickListener<ProjectDetails> {
    companion object{
        private const val TAG = "ListingProject"
        private const val PROJECT_LISTING_ACTING = 230
    }
    var projectId:Long = 0
    override fun onDataClicked(rowIndex: Int, clickedData: ProjectDetails?){
        clickedData!!.projectName?.let { Log.i("Test", it)
        projectId = clickedData!!.projectId}
        var intent = Intent(context, ProjectReadOnly::class.java)
        intent.putExtra("code", INETNT_FROM_TEAM_PRJ_LIST)
        intent.putExtra("projectId",projectId.toString() )
        intent.putExtra("name",name )
        intent.putExtra("role",role )
        intent.putExtra("email",email )
        intent.putExtra("profileImage",profileImage )

        context.startActivity(intent)
        Log.i("Test",projectId.toString())

    }
}