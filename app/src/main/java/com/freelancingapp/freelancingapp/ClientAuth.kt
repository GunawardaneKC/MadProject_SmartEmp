package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityClientAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClientAuth : AppCompatActivity() {
    private lateinit var binding: ActivityClientAuthBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnEdit.setOnClickListener {
            startActivity(Intent(this, ClientAuthEdit::class.java))
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


        val currentUser = auth.currentUser


        if (currentUser != null) {
            val email = currentUser.email
            val uid = currentUser.uid

            binding.viewEmail.text = "$email"

            database.child("Users").child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = snapshot.child("username").value.toString()
                    binding.viewUsername.text = "$username"
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors here
                }
            })
        }
    }
}