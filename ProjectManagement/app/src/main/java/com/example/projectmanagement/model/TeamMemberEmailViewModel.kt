package com.example.projectmanagement.model

import androidx.lifecycle.ViewModel
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.db.FirestoreRepository

class TeamMemberEmailViewModel (
    private val repository: FirestoreRepository = FirestoreRepository()
        ) : ViewModel() {

    fun getTeamMemberDetails(callback: FirestoreCallback) {
        repository.getTeamMemberDetailsUsingCallBack(callback)
    }
        }
