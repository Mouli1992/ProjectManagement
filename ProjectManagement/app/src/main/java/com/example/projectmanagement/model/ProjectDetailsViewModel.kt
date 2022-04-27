package com.example.projectmanagement.model

import androidx.lifecycle.ViewModel
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.db.FirestoreRepository

class ProjectDetailsViewModel (  private val repository: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    fun getProjectDetails(callback: FirestoreCallback){
        repository.getManagerProjectDetails(callback)

    }

}