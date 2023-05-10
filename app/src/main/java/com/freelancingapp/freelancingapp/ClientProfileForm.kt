package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityClientProfileFormBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ClientProfileForm : AppCompatActivity() {

    private lateinit var binding: ActivityClientProfileFormBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientProfileFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.clientFormSubmit.setOnClickListener {

            val userId = firebaseAuth.currentUser?.uid

            if (userId != null) {

                val clientData = ClientData(
                    binding.clientFirstName.text.toString(),
                    binding.clientLastName.text.toString(),
                    binding.clientCompanyName.text.toString(),
                    binding.clientPhone.text.toString(),
                    binding.clientAddress.text.toString(),
                    binding.clientRegion.text.toString(),
                    binding.clientCountry.text.toString(),
                    when (findViewById<RadioButton>(binding.radioGroup2.checkedRadioButtonId)) {
                        binding.clientMale -> "Male"
                        binding.clientFemale -> "Female"
                        else -> ""
                    }
                )

                createClientProfile(userId, clientData)

            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createClientProfile(userId: String, clientData: ClientData) {
        val clientsRef = FirebaseDatabase.getInstance().getReference("clients")

        clientsRef.child(userId).setValue(clientData)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile created successfully", Toast.LENGTH_SHORT).show()
                val intent1 = Intent(this, ClientMainProfile::class.java)
                startActivity(intent1)

            }
            .addOnFailureListener {
                Toast.makeText(this, "Error creating profile", Toast.LENGTH_LONG).show()
            }
    }
}


