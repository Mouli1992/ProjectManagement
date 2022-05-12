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
import android.widget.Button as Button


//const val TOPIC = "/topics/myTopic2"

class MainActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var pwd: EditText
    private lateinit var btnSignIn: Button
    private val database = Firebase.firestore


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
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)


        email = findViewById(R.id.editEmail)
        pwd = findViewById(R.id.editPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        val signUpBtn = findViewById<Button>(R.id.btnSignUp)

//        val title = "New Notification"
//        val message= "A new notification has been received"
//         val recipientToken = token
//
//        if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
//            PushNotification(
//                NotificationData(title, message),
//                recipientToken
//            ).also {
//                sendNotification(it)
//            }
//        }


//        private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = RetrofitInstance.api.postNotification(notification)
//                if (response.isSuccessful) {
//                    Log.d(TAG, "Response: ${Gson().toJson(response)}")
//                } else {
//                    Log.e(TAG, response.errorBody().toString())
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, e.toString())
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