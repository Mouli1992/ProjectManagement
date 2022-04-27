package com.example.projectmanagement.db

import com.example.projectmanagement.model.ProjectDetails

interface FirestoreCallback {
    fun onTeamMemberListCallBack(teamMemberLst : MutableList<String>)
    fun onProjectDetailsCallback(projectDetails : MutableList<ProjectDetails>)
}