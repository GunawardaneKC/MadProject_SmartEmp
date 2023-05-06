package com.freelancingapp.freelancingapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class UserSelect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select)
    }

    fun onUserFreelancerButtonClick(view: View) {
        startActivity(Intent(this, FreelancerProfileForm::class.java))
    }

    fun onUserClientButtonClick(view: View) {
        startActivity(Intent(this, ClientProfileForm::class.java))
    }
}