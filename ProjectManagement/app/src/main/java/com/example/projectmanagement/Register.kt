package com.example.projectmanagement

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.net.URI

class Register: AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var name:EditText
    private lateinit var mobile:EditText
    private lateinit var pwd:EditText
    private lateinit var regBtn: Button
    private lateinit var uploadP: ImageView
    private lateinit var addProfilePhoto: Button



    companion object{
        private const val TAG = "Register"
        private const val CREATE_REQUEST_CODE = 248
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registerlayout)

        name= findViewById(R.id.editFullNameRegister)
        email= findViewById(R.id.editEmailRegister)
        mobile= findViewById(R.id.editMobileNumberRegister)
        pwd= findViewById(R.id.editNewPasswordRegister)
        regBtn = findViewById(R.id.btnSignUpRegister)
        uploadP= findViewById(R.id.imgProfileImage)
        addProfilePhoto = findViewById<Button>(R.id.btnAddProfileImage)



        addProfilePhoto.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it,0)
            }
        }




        val signInButton = findViewById<Button>(R.id.btnSignInRegister)
        signInButton.setOnClickListener {
            callSignInButton()
        }



    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==0){
            val uri= data?.data
                uploadP.setImageURI(uri)
        }

    }

    private fun registerUser() {
        var firebaseUser : FirebaseUser? = null
        when{
            TextUtils.isEmpty(email.text.toString().trim {it <= ' '}) ->{
                Toast.makeText(this@Register,"Please enter email", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(pwd.text.toString().trim{it <= ' '}) ->{
                Toast.makeText(this@Register,"Please enter password", Toast.LENGTH_SHORT).show()
            }else -> {
            val email = email.text.toString().trim {it <= ' '}
            val pwd = pwd.text.toString().trim{it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseUser = task.result!!.user!!
                        Toast.makeText(this, "You are registered Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Register, MainActivity::class.java)
                        intent.putExtra("userId", firebaseUser?.uid)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
                    }
                }
                .addOnFailureListener{ exception ->
                    Log.e(TAG, "Encountered error while authenticating the user",exception)
                    Toast.makeText(this, "Encountered error while authentication", Toast.LENGTH_SHORT).show()
                }


        }


        }
    }

    private fun callSignInButton() {
        val intent = Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }

    }
}
