package com.example.projectmanagement.table.project

import com.example.projectmanagement.model.TaskDetails

class TaskComparator : Comparator<TaskDetails> {
    override fun compare(task1: TaskDetails?, task2: TaskDetails?): Int {
        return task1!!.taskDeadline!!.compareTo(task2!!.taskDeadline)
    }


}

