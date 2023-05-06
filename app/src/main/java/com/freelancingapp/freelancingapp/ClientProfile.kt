package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityClientProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClientProfile : AppCompatActivity() {

    private lateinit var binding: ActivityClientProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var clientsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        clientsRef = FirebaseDatabase.getInstance().getReference("clients")

        val userId = firebaseAuth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        clientsRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val client = snapshot.getValue(Client::class.java)
                if (client != null) {
                    binding.clientFirstName.text = client.firstname
                    binding.clientLastName.text = client.lastname
                    binding.clientCompanyName.text = client.company
                    binding.clientPhone.text = client.phone_number
                    binding.clientAddress.text = client.address
                    binding.clientRegion.text = client.region
                    binding.clientCountry.text = client.country
                } else {
                    Toast.makeText(this@ClientProfile, "Client data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ClientProfile, "Error retrieving data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.editClientDetaills.setOnClickListener {
            startActivity(Intent(this, ClientDetailEdit::class.java))
        }
    }

    data class Client(
        val phone_number: String? = "",
        val firstname: String? = "",
        val lastname: String? = "",
        val company: String? = "",
        val address: String? = "",
        val region: String? = "",
        val country: String? = ""
    )
}