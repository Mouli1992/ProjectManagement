package com.example.projectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.projectmanagement.model.UserDetails
import com.example.projectmanagement.utils.INTENT_FROM_LOGIN
import com.example.projectmanagement.utils.USER_ROLE_MANAGER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.Button as Button




class MainActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var pwd: EditText
    private lateinit var btnSignIn: Button
    private val database = Firebase.firestore


    companion object{
        private const val TAG="MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.editEmail)
        pwd = findViewById(R.id.editPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        val signUpBtn = findViewById<Button>(R.id.btnSignUp)
        signUpBtn.setOnClickListener {
            callRegisterButton()
        }

        btnSignIn.setOnClickListener {
            validateUserCred()
        }

    }

    private fun callRegisterButton() {
        val intent = Intent(this, Register::class.java).also {
            startActivity(it)
            }
        }
    private fun validateUserCred() {
        var firebaseUser : FirebaseUser? = null
        when{
            TextUtils.isEmpty(email.text.toString().trim {it <= ' '}) ->{
                Toast.makeText(this@MainActivity,"Please enter email", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(pwd.text.toString().trim{it <= ' '}) ->{
                Toast.makeText(this@MainActivity,"Please enter password", Toast.LENGTH_SHORT).show()
            }else -> {
            val email = email.text.toString().trim {it <= ' '}
            val pwd = pwd.text.toString().trim{it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseUser = task.result!!.user!!
                        Toast.makeText(this, "You are signed in Successfully", Toast.LENGTH_SHORT).show()
                        database.collection("userDetails").document(email).get().addOnSuccessListener { document ->
                            val userDetails = document.toObject(UserDetails::class.java)
                            if(userDetails?.role.equals(USER_ROLE_MANAGER)){
                                val intent = Intent(this@MainActivity, ListingProject::class.java)
                                intent.putExtra("userId", firebaseUser?.uid)
                                intent.putExtra("email", email)
                                intent.putExtra("role", userDetails?.role)
                                intent.putExtra("mobileNo", userDetails?.mobileNo)
                                intent.putExtra("code", INTENT_FROM_LOGIN)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this@MainActivity, ListingProject::class.java)
                                intent.putExtra("userId", firebaseUser?.uid)
                                intent.putExtra("email", email)
                                intent.putExtra("role", userDetails?.role)
                                intent.putExtra("mobileNo", userDetails?.mobileNo)
                                startActivity(intent)
                                finish()

                            }

                        }




                    }
                }
                .addOnFailureListener{ exception ->
                    Log.e(TAG, "Encountered error while authenticating the user",exception)
                    Toast.makeText(this, "Encountered error while authentication", Toast.LENGTH_SHORT).show()
                }


        }


        }
    }


}