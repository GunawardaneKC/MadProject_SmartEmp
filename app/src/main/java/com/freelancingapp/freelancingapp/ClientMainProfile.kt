package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ClientMainProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main_profile)
    }

    fun clientFreelanBtnMain(view: View) {
        startActivity(Intent(this, FreelancerFetch::class.java))
    }
    
    fun clientProfileBtnMain(view: View) {
        startActivity(Intent(this, ClientAuth::class.java))
    }

    fun orderRedirectBtnMain(view: View) {
        startActivity(Intent(this, OrderLobby::class.java))
    }

    fun messageRedirectBtnMain(view: View) {
        startActivity(Intent(this, MessageLobby::class.java))
    }

    fun personalInformationBtnMain(view: View) {
        startActivity(Intent(this, ClientProfile::class.java))
    }
    fun signoutbutton(view: View) {
        startActivity(Intent(this, activity_login::class.java))
    }
}