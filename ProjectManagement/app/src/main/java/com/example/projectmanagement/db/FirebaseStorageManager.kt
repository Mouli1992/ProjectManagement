package com.example.projectmanagement.db

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.projectmanagement.MainActivity
import com.example.projectmanagement.Register
import com.example.projectmanagement.model.UserDetails
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseStorageManager {

    companion object{
        private const val TAG="FirebaseStorageManager"
    }
    private var database = Firebase.firestore
    private val storageRef = FirebaseStorage.getInstance().reference
    private lateinit var uploadedUri1: String

    fun registerUser(context: Context, imageFileUri: Uri, userDetails: UserDetails) : String {
        val date = Date()
        uploadedUri1 =""
        val uploadTask = storageRef.child("users/${date}.png").putFile(imageFileUri)
        uploadTask.addOnSuccessListener {
            Log.i("Firebase", "Image Upload success")
            val uploadedUri = storageRef.child("users/${date}.png").downloadUrl
            uploadedUri.addOnSuccessListener {
                uploadedUri1 = it.toString()
                userDetails.pictureUri=it.toString()
                saveUserDetails(userDetails,context)
                Log.i("Firebase uploadedUri1 inside it", "Uploaded $uploadedUri1")
            }
            Log.i("Firebase", "Uploaded $uploadedUri")
            Log.i("Firebase uploadedUri1", "Uploaded $uploadedUri1")
        }.addOnFailureListener {
            Log.e("Firebase", "Image Upload fail")
        }
        return uploadedUri1
    }


    private fun saveUserDetails( userDetails: UserDetails, context: Context) { database.collection("userDetails").document(
        userDetails!!.email!!
    ).set(userDetails).addOnCompleteListener { userDetailsTask ->
            if(userDetailsTask.isSuccessful) {
                Toast.makeText(context, "You are registered Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("name", userDetails.name)
                intent.putExtra("email", userDetails.email)
                context.startActivity(intent)

            }else{
                Log.e(TAG, "Exception with User Registration")
                Toast.makeText(context, "User registration Failed", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
        }



    }
}