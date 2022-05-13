package com.example.projectmanagement.table.project

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.projectmanagement.ProjectReadOnly
import com.example.projectmanagement.TeamMemberTaskList
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.utils.INTENT_FROM_PRJ_LST_FOR_PRJ
import de.codecrafters.tableview.listeners.TableDataClickListener

class TaskClickListener(private val context: Context): TableDataClickListener<ProjectDetails> {

    companion object{
        private const val TAG = "ListingProject"
        private const val PROJECT_LISTING_ACTING = 240
    }
    var projectId:Long = 0
    override fun onDataClicked(rowIndex: Int, clickedData: ProjectDetails?){
        clickedData!!.projectName?.let { Log.i("TestTask", it)
            projectId = clickedData!!.projectId}
        var intent = Intent(context, TeamMemberTaskList::class.java)
        intent.putExtra("code", INTENT_FROM_PRJ_LST_FOR_PRJ)
        intent.putExtra("projectId",projectId.toString() )
        context.startActivity(intent)
        Log.i("Test",projectId.toString())

    }
}

