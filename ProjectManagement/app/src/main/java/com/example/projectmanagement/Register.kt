package com.example.projectmanagement

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.projectmanagement.db.FirebaseStorageManager
import com.example.projectmanagement.model.UserDetails
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Register: AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var name:EditText
    private lateinit var mobile:EditText
    private lateinit var pwd:EditText
//    private lateint var regBtn: Button
    private lateinit var uploadP: ImageView
    private lateinit var addProfilePhoto: Button
    private lateinit var signInBtn: Button
    private lateinit var roleSpinner : Spinner
    private lateinit var signUpBtn : Button
    private lateinit var selectedRole :String
    private val database = Firebase.firestore
    private val storage =Firebase.storage
    private lateinit var uri : Uri
    private lateinit var context: Context

    companion object{
        private const val TAG = "Register"
        private const val CREATE_REQUEST_CODE = 248
        private const val IMAGE_SELECTION_CODE = 250
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProjectManagement)
        setContentView(R.layout.registerlayout)



        name= findViewById(R.id.editFullNameRegister)
        email= findViewById(R.id.editEmailRegister)
        mobile= findViewById(R.id.editMobileNumberRegister)
        pwd= findViewById(R.id.editNewPasswordRegister)
//        regBtn = findViewById(R.id.btnSignUpRegister)
        uploadP= findViewById(R.id.imgProfileImage)
        addProfilePhoto = findViewById<Button>(R.id.btnAddProfileImage)
        signInBtn =findViewById(R.id.btnSignInRegister)
        signUpBtn = findViewById(R.id.btnSignUpRegister)
        roleSpinner = findViewById(R.id.roleSpinner)
        getRoleSelected()
        context = this
        addProfilePhoto.setOnClickListener {
            choosePictures()
//            Intent(Intent.ACTION_GET_CONTENT).also {
//                it.type = "image/*"
//                startActivityForResult(it,0)
//            }
        }
        signInBtn.setOnClickListener{
            navigateToSignInActivity()
        }

        signUpBtn.setOnClickListener {
            registerUser()
        }

    }

    private fun choosePictures() {
        var intent : Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, IMAGE_SELECTION_CODE,)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==IMAGE_SELECTION_CODE && data!= null &&
                data.data != null){

            uri= data?.data!!
            uploadP.setImageURI(uri)
            uploadP.scaleType = ImageView.ScaleType.CENTER_CROP

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
                        val userDetails = UserDetails(email, name.text.toString(),Integer.parseInt(mobile.text.toString()),Timestamp.now(),selectedRole,"")
                        if(null == uri){
                            uri = Uri.EMPTY
                        }

                        FirebaseStorageManager().registerUser(context,uri,userDetails)
                    //saveUserDetails(email,name.text.toString(),Integer.parseInt(mobile.text.toString()),selectedRole,uri)

                    }
                }
                .addOnFailureListener{ exception ->
                    Log.e(TAG, "Encountered error while authenticating the user",exception)
                    Toast.makeText(this, "Encountered error while authentication", Toast.LENGTH_SHORT).show()
                }


        }

        }
    }

    private fun navigateToSignInActivity() {
        val intent = Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }



    }
}
