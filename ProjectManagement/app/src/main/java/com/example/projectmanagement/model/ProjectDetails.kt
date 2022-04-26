package com.example.projectmanagement.model

import com.google.firebase.Timestamp

data class ProjectDetails(
    val projectId: Long = -1,
    val projectName: String? = null,
    val projectCreatedBy: String?= null,
    var projectStatus: String?= null,
    val projectDeadline: Timestamp? = Timestamp.now(),
    val taskLst: MutableList<TaskDetails>? = mutableListOf(),
    val createdAt : Timestamp? = Timestamp.now()
)
