package com.example.projectmanagement.model

import androidx.lifecycle.ViewModel
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.db.FirestoreRepository

class ProjectDetailsViewModel (  private val repository: FirestoreRepository = FirestoreRepository(),
                                 private val email: String =""
) : ViewModel() {

    fun getProjectDetails(callback: FirestoreCallback, email: String){
        repository.getManagerProjectDetails(callback,email)

    }

}