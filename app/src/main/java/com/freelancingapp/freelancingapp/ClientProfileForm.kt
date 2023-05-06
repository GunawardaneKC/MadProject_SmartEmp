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

                val client_first_name = binding.clientFirstName.text.toString()
                val client_last_name = binding.clientLastName.text.toString()
                val client_company_name = binding.clientCompanyName.text.toString()
                val client_phone = binding.clientAddress.text.toString()
                val client_address = binding.clientAddress.text.toString()
                val client_region = binding.clientRegion.text.toString()
                val client_country = binding.clientCountry.text.toString()
                val radioGroup2 = when (findViewById<RadioButton>(binding.radioGroup2.checkedRadioButtonId)) {
                    binding.clientMale -> "Male"
                    binding.clientFemale -> "Female"
                    else -> ""
                }

                fun createClientProfile(userId: String, firstname: String, lastname: String) {
                    val clientsRef = FirebaseDatabase.getInstance().getReference("clients")

                    val clientData = mapOf(
                        "firstname" to client_first_name,
                        "lastname" to client_last_name,
                        "company" to client_company_name,
                        "phone_number" to client_phone,
                        "address" to client_address,
                        "region" to client_region,
                        "country" to client_country,
                        "Gender" to radioGroup2,
                    )

                    clientsRef.child(userId).setValue(clientData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Profile created successfully", Toast.LENGTH_SHORT)
                                .show()
                            val intent1 = Intent(this, ClientMainProfile::class.java)
                            startActivity(intent1)

                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error creating profile", Toast.LENGTH_LONG)
                                .show()
                        }
                }
                createClientProfile(userId.toString(), client_first_name, client_last_name)
            }else{
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
