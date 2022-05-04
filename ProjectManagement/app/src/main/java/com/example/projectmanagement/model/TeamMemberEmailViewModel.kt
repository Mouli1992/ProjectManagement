package com.example.projectmanagement.model

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.db.FirestoreRepository

class TeamMemberEmailViewModel(
    private val repository: FirestoreRepository = FirestoreRepository(),
    private val role: String = ""
        ) : ViewModel() {

    fun getTeamMemberDetails(callback: FirestoreCallback,role: String) {
        repository.getTeamMemberDetailsUsingCallBack(callback,role)
    }
        }
