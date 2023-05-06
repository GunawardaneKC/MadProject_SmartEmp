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

    fun gotoFreelancerView(view: View) {
        startActivity(Intent(this, ClientProfile::class.java))
    }
    
    fun clientProfileBtnMain(view: View) {
        startActivity(Intent(this, ClientAuth::class.java))
    }

    fun orderRedirectBtnMain(view: View) {
        startActivity(Intent(this, ClientOrders::class.java))
    }

    fun paymentRedirectBtnMain(view: View) {
        startActivity(Intent(this, ClientPayment::class.java))
    }

    fun personalInformationBtnMain(view: View) {
        startActivity(Intent(this, ClientProfile::class.java))
    }
}