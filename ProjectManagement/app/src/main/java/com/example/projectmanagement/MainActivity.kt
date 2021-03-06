package com.example.projectmanagement

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projectmanagement.model.UserDetails
import com.example.projectmanagement.utils.INTENT_FROM_LOGIN
import com.example.projectmanagement.utils.USER_ROLE_MANAGER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import de.mateware.snacky.Snacky
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Button as Button


//const val TOPIC = "/topics/myTopic2"

class MainActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var pwd: EditText
    private lateinit var btnSignIn: Button
    private val database = Firebase.firestore
    private val title = "Task Notification"
    private val message= "Task Assigned"

//    val TAG = "MainActivity"

    companion object{
        private const val TAG="MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProjectManagement)
        setContentView(R.layout.activity_main)


//        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
//            FirebaseService.token = it.token
//             val token = it.token
//        }
//       FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        //RecipentToken
        email = findViewById(R.id.editEmail)
        pwd = findViewById(R.id.editPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        val signUpBtn = findViewById<Button>(R.id.btnSignUp)


//         val recipientToken = token
//
//        if(title.isNotEmpty() && message.isNotEmpty()) {
//            PushNotification(
//                NotificationData(title, message),
//                TOPIC
//            ).also {
//                sendNotification(it)
//            }
//        }




        signUpBtn.setOnClickListener {
            callRegisterButton()
        }

        btnSignIn.setOnClickListener {

            validateUserCred()
        }


//        to add forget password hyperlink
        val linkTextView = findViewById<TextView>(R.id.txtForgetPassword)
        linkTextView.setOnClickListener {
            val switchActivityIntent = Intent(this, Register::class.java)
            startActivity(switchActivityIntent)
        }

    }

    private fun callRegisterButton() {
        Intent(this, Register::class.java).also {
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
            val email = email.text.toString().trim { it <= ' ' }
            val pwd = pwd.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        if(title.isNotEmpty() && message.isNotEmpty()) {
//                            PushNotification(
//                                NotificationData(title, message) ).also {
//                                sendNotification(it)
//                            }
//                        }
                        firebaseUser = task.result!!.user!!
                        Toast.makeText(this, "You are signed in Successfully", Toast.LENGTH_SHORT)
                            .show()
                        database.collection("userDetails").document(email).get()
                            .addOnSuccessListener { document ->
                                val userDetails = document.toObject(UserDetails::class.java)
                                println("userDetails${userDetails?.role} ")
                                if (userDetails?.role.equals(USER_ROLE_MANAGER)) {
                                    val intent =
                                        Intent(this@MainActivity, ListingProject::class.java)
                                    intent.putExtra("userId", firebaseUser?.uid)
                                    intent.putExtra("email", email)
                                    intent.putExtra("role", userDetails?.role)
                                    intent.putExtra("mobileNo", userDetails?.mobileNo)
                                    intent.putExtra("profileImage", userDetails?.pictureUri)
                                    intent.putExtra("code", INTENT_FROM_LOGIN)
                                    intent.putExtra("name", userDetails?.name)
                                    startActivity(intent)
                                    println("userDetails${userDetails?.role} ")
                                    finish()
                                } else {
                                    val intent = Intent(
                                        this@MainActivity,
                                        ListingProjectTeamMember::class.java)
                                    intent.putExtra("userId", firebaseUser?.uid)
                                    intent.putExtra("email", email)
                                    intent.putExtra("role", userDetails?.role)
                                    intent.putExtra("mobileNo", userDetails?.mobileNo)
                                    intent.putExtra("profileImage", userDetails?.pictureUri)
                                    intent.putExtra("name", userDetails?.name)
                                    startActivity(intent)
                                    println("userDetails${userDetails?.role} ")
                                    finish()

                                }

                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Encountered error while authenticating the user", exception)
                    Toast.makeText(
                        this,
                        "Encountered error while authentication",
                        Toast.LENGTH_SHORT
                    ).show()
                    Snacky.builder()
                        .setActivity(this)
                        .setText("Invalid Username or Password")
                        .setDuration(Snacky.LENGTH_INDEFINITE)
                        .setActionText(android.R.string.ok)
                        .error()
                        .show();

                }


        }
        }
    }


}