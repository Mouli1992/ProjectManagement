package com.example.projectmanagement.db

import android.util.Log
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.UserDetails
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepository {
    private var database = Firebase.firestore

    companion object{
        const val TAG = "FirestoreRepository"
    }

   fun getTeamMemberDetailsUsingCallBack(callback: FirestoreCallback,  role : String){
       var teamMemberEmailLst: MutableList<String> = mutableListOf()
       var userDetailsLst = mutableListOf<UserDetails>()
       database.collection("userDetails").
       whereEqualTo("role",role).get().addOnCompleteListener{ task ->
           if(task.isSuccessful){
               userDetailsLst = task.result.toObjects(UserDetails::class.java)
               for(userDetails in userDetailsLst){
                   teamMemberEmailLst.add(userDetails?.email!!)
               }
               println(teamMemberEmailLst)
               callback.onTeamMemberListCallBack(teamMemberEmailLst)
               return@addOnCompleteListener

           }else{
               Log.d(TAG, "Error in getTeamMember Details :",task.exception)
           }
       }
   }

    fun getManagerProjectDetails(projectDetailsCallback: FirestoreCallback, email : String){
        var projectDetailsLst = mutableListOf<ProjectDetails>()
        database.collection("projectDetails").
        whereEqualTo("projectCreatedBy",email).get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                projectDetailsLst = task.result.toObjects(ProjectDetails::class.java)
            }else{
                Log.e(TAG, "Error in getManagerProjectDetails Details :",task.exception)
            }
            projectDetailsCallback.onProjectDetailsCallback(projectDetailsLst)
            return@addOnCompleteListener

        }
    }
    fun getOneProjectDetails(projectDetailsCallback: FirestoreCallback, projectId : Long){
        var projectDetails = mutableListOf<ProjectDetails>()
        database.collection("projectDetails").
        whereEqualTo("projectId",projectId).get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                projectDetails = task.result.toObjects(ProjectDetails::class.java)
                println(projectDetails)
            }else{
                Log.e(TAG, "Error in getManagerProjectDetails Details :",task.exception)
            }
            projectDetailsCallback.onOneProjectDetailsCallback(projectDetails[0])
            return@addOnCompleteListener

        }

        }
}