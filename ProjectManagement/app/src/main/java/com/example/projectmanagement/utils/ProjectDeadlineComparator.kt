package com.example.projectmanagement.utils

import com.example.projectmanagement.model.ProjectDetails

class ProjectDeadlineComparator : Comparator<ProjectDetails>{
    override fun compare(project1: ProjectDetails?, project2: ProjectDetails?): Int {
        return project1!!.projectDeadline!!.compareTo(project2!!.projectDeadline!!)
    }
}

class ProjectNameComparator : Comparator<ProjectDetails>{
    override fun compare(project1: ProjectDetails?, project2: ProjectDetails?): Int {
        return project1!!.projectName!!.compareTo(project2!!.projectName!!)
    }
}

class ProjectStatusComparator : Comparator<ProjectDetails>{
    override fun compare(project1: ProjectDetails?, project2: ProjectDetails?): Int {
        return project1!!.projectStatus!!.compareTo(project2!!.projectStatus!!)
    }
}