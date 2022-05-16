package com.example.projectmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanagement.db.FirestoreCallback
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.model.TeamMemberProjectDetailsViewModel
import com.example.projectmanagement.table.team.TaskClickListener
import com.example.projectmanagement.table.team.TeamMemberTableAdaptor
import com.example.projectmanagement.utils.TableHeader
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.codecrafters.tableview.TableView

class ListingProjectTeamMember : AppCompatActivity() {

    private lateinit var tableView : TableView<ProjectDetails>
    private var database = Firebase.firestore
    private lateinit var projectDetails : ProjectDetails
    private lateinit var context: Context
    private lateinit var teamMemberProjectViewModel: TeamMemberProjectDetailsViewModel
    private lateinit var profilePic : ImageView
    private lateinit var name : TextView
    private lateinit var role : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_project_team_member)
        tableView = findViewById(R.id.tableViewProjectListingTeamMember)
        tableView.columnCount=3
        teamMemberProjectViewModel = ViewModelProvider(this).get(TeamMemberProjectDetailsViewModel::class.java)
        projectDetails = ProjectDetails()
        context = this
//        intent.putExtra("userId", firebaseUser?.uid)
//        intent.putExtra("email", email)
//        intent.putExtra("role", userDetails?.role)
//        intent.putExtra("mobileNo", userDetails?.mobileNo)
//        intent.putExtra("profileImage", userDetails?.pictureUri)
        intent.getStringExtra("email")?.let { getTeamMemberProjectDetails(it) }
        println("name "+intent.getStringExtra("name") )
        println("profileImage "+intent.getStringExtra("profileImage") )
        tableView.addDataClickListener(intent.getStringExtra("email")?.let {
            TaskClickListener(this,
                it, intent.getStringExtra("role")!!, intent.getStringExtra("name")!!, intent.getStringExtra("profileImage")!!
            )
        })
        tableView.isSwipeToRefreshEnabled= true
        profilePic = findViewById(R.id.imageTeamMember)
        name = findViewById(R.id.txtMemberUserName)
        role = findViewById(R.id.txtMemberUserRole)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heading_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuLogout-> Intent(this@ListingProjectTeamMember, MainActivity::class.java).also {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTeamMemberProjectDetails(email : String) {

        teamMemberProjectViewModel.getProjectDetails(object : FirestoreCallback {
            override fun onTeamMemberListCallBack(teamMemberLst: MutableList<String>) {
                TODO("Not yet implemented")
            }

            override fun onProjectDetailsCallback(projectDetails: MutableList<ProjectDetails>) {
                TODO("Not yet implemented")
            }

            override fun onOneProjectDetailsCallback(projectDetails: ProjectDetails) {
                TODO("Not yet implemented")
            }

            override fun onTeamMemberProjectCallback(projectDetails: MutableList<ProjectDetails>) {
                val adapter = TeamMemberTableAdaptor(context,projectDetails,tableView)
                tableView.dataAdapter = adapter
                tableView.headerAdapter = TableHeader.getProjectTableHeader(context, "")
                name.text = "Name :"+intent.getStringExtra("name")
                role.text = "Role :"+intent.getStringExtra("role")

                println("Profile URL"+intent.getStringExtra("profileImage"))
                if(null != intent.getStringExtra("profileImage")) {
                    intent.putExtra("profileImage",intent.getStringExtra("profileImage"))
                    Picasso.get().load(intent.getStringExtra("profileImage")).into(profilePic);
                    profilePic.scaleType = ImageView.ScaleType.CENTER_CROP
                }

            }

        }, email = email)
    }


    }