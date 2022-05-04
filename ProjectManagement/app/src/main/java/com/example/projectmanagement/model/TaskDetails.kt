package com.example.projectmanagement.model

import com.google.firebase.Timestamp

data class TaskDetails(
    var taskId: Long = -1,
    var taskName: String? = null,
    var taskStatus: String? = null,
    var taskDeadline: Timestamp? = Timestamp.now(),
    var assignedTo: String? = null
)
