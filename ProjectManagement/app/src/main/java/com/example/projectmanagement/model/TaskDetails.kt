package com.example.projectmanagement.model

import com.google.firebase.Timestamp

data class TaskDetails(
    val taskId: Long = -1,
    val taskName: String? = null,
    val taskStatus: String? = null,
    val taskDeadline: Timestamp? = Timestamp.now(),
    val assignedTo: String? = null
)
