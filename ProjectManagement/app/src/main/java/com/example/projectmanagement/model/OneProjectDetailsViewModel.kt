package com.example.projectmanagement.model

import androidx.lifecycle.ViewModel
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.db.FirestoreRepository

class OneProjectDetailsViewModel (private val repository: FirestoreRepository = FirestoreRepository(),
                                  private val projectId: Long =-1
) : ViewModel() {

    fun getOneProjectDetails(callback: FirestoreCallback, projectId: Long){
        repository.getOneProjectDetails(callback,projectId)

    }
}