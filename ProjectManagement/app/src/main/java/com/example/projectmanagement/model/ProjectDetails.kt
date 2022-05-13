package com.example.projectmanagement.model

import com.google.firebase.Timestamp

data class ProjectDetails(
    var projectId: Long = -1,
    var projectName: String? = null,
    var projectCreatedBy: String?= null,
    var projectStatus: String?= null,
    var projectDeadline: Timestamp? = Timestamp.now(),
    var taskLst: MutableList<TaskDetails>? = mutableListOf(),
    var createdAt : Timestamp? = Timestamp.now(),
    var teamLst : MutableList<String>? = mutableListOf()
)
