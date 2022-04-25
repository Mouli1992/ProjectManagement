package com.example.projectmanagement.model

import com.google.firebase.Timestamp


data class UserDetails(
    val email: String? = null,
    val name : String? = null,
    val mobileNo : Int? = -1,
    val createdAt : Timestamp? = Timestamp.now(),
    val role: String? = null
)

