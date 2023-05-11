package com.freelancingapp.freelancingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityFreelancerProfileFormBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FreelancerProfileForm : AppCompatActivity() {

    private lateinit var binding: ActivityFreelancerProfileFormBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFreelancerProfileFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.freelancerFormSubit.setOnClickListener{

            val userId = firebaseAuth.currentUser?.uid

            if (userId != null) {

                val firstNameFreelancer = binding.freelancerFirstName.text.toString()
                val lastNameFreelancer = binding.freelancerLastName.text.toString()
                val phoneFreelancer = binding.freelancerPhone.text.toString()
                val addressFreelancer = binding.freelancerAddress.text.toString()
                val skillsFreelancer = binding.freelancerSkills.text.toString()
                val radioGroupFreelancer = when (findViewById<RadioButton>(binding.radiogroupFreelancer.checkedRadioButtonId)) {
                    binding.freelancerMale -> "Male"
                    binding.freelancerFemale -> "Female"
                    else -> ""
                }

                val freelancerProfile = FreelancerData(
                    firstNameFreelancer,
                    lastNameFreelancer,
                    phoneFreelancer,
                    addressFreelancer,
                    skillsFreelancer,
                    radioGroupFreelancer
                )
                    if (firstNameFreelancer.isEmpty()){
                        binding.freelancerFirstName.error = "Please enter First Name"
                }
                if (lastNameFreelancer.isEmpty()){
                    binding.freelancerLastName.error = "Please enter Last Name"
                }
                if (phoneFreelancer.isEmpty()){
                    binding.freelancerPhone.error = "Please enter Phone Number"
                }
                if (addressFreelancer.isEmpty()){
                    binding.freelancerAddress.error = "Please enter tour Address"
                }
                else {

                        createFreelancerProfile(userId.toString(), freelancerProfile)
                    }
            }else{
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createFreelancerProfile(userId: String, freelancerProfile: FreelancerData) {
        val freelancerRef = FirebaseDatabase.getInstance().getReference("freelancers")

        freelancerRef.child(userId).setValue(freelancerProfile)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile created successfully", Toast.LENGTH_SHORT)
                    .show()
                val intent1 = Intent(this, FreelancerMainProfile::class.java)
                startActivity(intent1)

            }
            .addOnFailureListener {
                Toast.makeText(this, "Error creating profile", Toast.LENGTH_LONG)
                    .show()
            }
    }
}

