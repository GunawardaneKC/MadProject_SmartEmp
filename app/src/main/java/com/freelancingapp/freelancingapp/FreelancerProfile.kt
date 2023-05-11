package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityFreelancerProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FreelancerProfile : AppCompatActivity() {

    private lateinit var binding: ActivityFreelancerProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var clientsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFreelancerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        clientsRef = FirebaseDatabase.getInstance().getReference("freelancers")

        val userId = firebaseAuth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return}

        clientsRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val client = snapshot.getValue(FreelancerData::class.java)
                if (client != null) {
                    val a = client.firstname
                    val b = client.lastname
                    val c = client.phone_number
                    val d = client.address
                    val e = client.skills
                    binding.freelancerFirstName.text = "first name : $a"
                    binding.freelancerLastName.text = "Last Name : $b"
                    binding.freelancerPhone.text = "Number : $c"
                    binding.freelancerAddress.text = "Address : $d"
                    binding.freelancerSkills.text = "Skills : $e"
                } else {
                    Toast.makeText(this@FreelancerProfile, "Client data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FreelancerProfile, "Error retrieving data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.freelancerFormedit.setOnClickListener {
            startActivity(Intent(this, FreelancerDetailEdit::class.java))
        }

    }
}