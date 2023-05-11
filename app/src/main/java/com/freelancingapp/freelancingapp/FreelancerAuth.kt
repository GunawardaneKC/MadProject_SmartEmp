package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityFreelancerAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FreelancerAuth : AppCompatActivity() {
    private lateinit var binding: ActivityFreelancerAuthBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreelancerAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        currentUser = auth.currentUser!!

        val email = currentUser.email
        val uid = currentUser.uid

        binding.viewEmail.text = "Email: $email"

        database.child("Users").child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val username = snapshot.child("username").value.toString()
                binding.viewUsername.text = "Username: $username"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FreelancerAuth, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.FreelancerEditBtn.setOnClickListener {
            startActivity(Intent(this@FreelancerAuth, FreelancerAuthEdit::class.java))
        }
    }
}
