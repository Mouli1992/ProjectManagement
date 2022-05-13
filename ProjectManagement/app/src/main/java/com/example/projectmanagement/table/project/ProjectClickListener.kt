package com.example.projectmanagement.table.project

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.projectmanagement.ListingProject
import com.example.projectmanagement.ProjectReadOnly
import com.example.projectmanagement.TeamMemberTaskList
import com.example.projectmanagement.db.FirestoreRepository
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.utils.INETNT_FROM_TEAM_PRJ_LIST
import com.example.projectmanagement.utils.INTENT_FROM_LOGIN
import com.example.projectmanagement.utils.INTENT_FROM_PRJ_LST_FOR_PRJ
import com.google.firebase.analytics.connector.R
import de.codecrafters.tableview.listeners.TableDataClickListener


class ProjectClickListener (private val context: Context):TableDataClickListener<ProjectDetails> {
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
        context.startActivity(intent)
        Log.i("Test",projectId.toString())

    }
}