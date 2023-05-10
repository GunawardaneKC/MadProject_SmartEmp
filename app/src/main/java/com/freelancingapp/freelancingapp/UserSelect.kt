package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserSelect : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
    }

    fun onUserFreelancerButtonClick(view: View) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        databaseReference.child(userId ?: "").child("userType").setValue("freelancer")
        startActivity(Intent(this, FreelancerProfileForm::class.java))
    }

    fun onUserClientButtonClick(view: View) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        databaseReference.child(userId ?: "").child("userType").setValue("client")
        startActivity(Intent(this, ClientProfileForm::class.java))
    }
}