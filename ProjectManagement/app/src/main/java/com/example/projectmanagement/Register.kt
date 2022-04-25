package com.example.projectmanagement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.projectmanagement.model.UserDetails
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register: AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var name:EditText
    private lateinit var mobile:EditText
    private lateinit var pwd:EditText
    private lateinit var regBtn: Button
    private lateinit var uploadP: ImageView
    private lateinit var addProfilePhoto: Button
    private lateinit var signInBtn: Button
    private lateinit var roleSpinner : Spinner
    private lateinit var signUpBtn : Button
    private lateinit var selectedRole :String
    private val database = Firebase.firestore


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
        signInBtn =findViewById(R.id.btnSignInRegister)
        signUpBtn = findViewById(R.id.btnSignUpRegister)
        roleSpinner = findViewById(R.id.roleSpinner)
        getRoleSelected()
        addProfilePhoto.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it,0)
            }
        }
        signInBtn.setOnClickListener{
            navigateToSignInActivity()
        }

        signUpBtn.setOnClickListener {
            registerUser()
        }




    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==0){
            val uri= data?.data
                uploadP.setImageURI(uri)
        }


    }

    private fun getRoleSelected() {
        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val role = parent!!.getItemAtPosition(position)
                selectedRole = role.toString()
                Log.i(TAG,"Role selected $selectedRole")

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
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
            }
            selectedRole.contentEquals("Role") ->{
                Toast.makeText(this@Register,"Please Select Role", Toast.LENGTH_SHORT).show()
            }
            else -> {
            val email = email.text.toString().trim {it <= ' '}
            val pwd = pwd.text.toString().trim{it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseUser = task.result!!.user!!
                        saveUserDetails(email,name.text.toString(),Integer.parseInt(mobile.text.toString()),selectedRole)

                    }
                }
                .addOnFailureListener{ exception ->
                    Log.e(TAG, "Encountered error while authenticating the user",exception)
                    Toast.makeText(this, "Encountered error while authentication", Toast.LENGTH_SHORT).show()
                }


        }

        }
    }

    private fun saveUserDetails(email: String, name: String, mobileNo: Int, role: String) {
        val userDetails = UserDetails(email,name,mobileNo, Timestamp.now(),role)
        database.collection("userDetails").document(email).set(userDetails).addOnCompleteListener { userDetailsTask ->
            if(userDetailsTask.isSuccessful) {
                Toast.makeText(this, "You are registered Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Register, MainActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()

            }else{
                Log.e(TAG, "Exception with User Registration")
                Toast.makeText(this, "User registeration Failed", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }



        }



    }

    private fun navigateToSignInActivity() {
        val intent = Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }



    }
}
