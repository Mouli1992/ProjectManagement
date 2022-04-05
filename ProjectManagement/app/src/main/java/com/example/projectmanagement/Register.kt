package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register: AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var name:EditText
    private lateinit var mobile:EditText
    private lateinit var pwd:EditText
    private lateinit var regBtn: Button

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


        val signInButton = findViewById<Button>(R.id.btnSignInRegister)
        signInButton.setOnClickListener {
            callSignInButton()
        }

        regBtn.setOnClickListener{
            registerUser()
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
