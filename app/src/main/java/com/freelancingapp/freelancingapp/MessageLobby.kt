package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MessageLobby : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_lobby)
    }

    fun AddVacancys(view: View) {
        startActivity(Intent(this, AddVacancy::class.java))
    }

    fun sentMessages(view: View) {
        startActivity(Intent(this, AddedVacancy::class.java))
    }

}