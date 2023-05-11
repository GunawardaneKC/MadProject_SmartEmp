package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class FreelancerMainProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freelancer_main_profile)

        val signOutButton = findViewById<Button>(R.id.freelancerSignOut)
        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, activity_login::class.java))
            finish()
        }
    }

    fun freelancerProfileBtnMain(view: View) {
        startActivity(Intent(this, FreelancerAuth::class.java))
    }

    fun freelancerOrdersRedirectBtnMain(view: View) {
        startActivity(Intent(this, OrderFetch::class.java))
    }

    fun freelancerPersonalInfoRedirect(view: View) {
        startActivity(Intent(this, FreelancerProfile::class.java))
    }

}