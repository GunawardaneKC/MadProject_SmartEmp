package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class OrderLobby : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_lobby)
    }

    fun AddOrder(view: View) {
        startActivity(Intent(this, ClientSetOrders::class.java))
    }

    fun AddedOrder(view: View) {
        startActivity(Intent(this, OrderFormRead::class.java))
    }

}