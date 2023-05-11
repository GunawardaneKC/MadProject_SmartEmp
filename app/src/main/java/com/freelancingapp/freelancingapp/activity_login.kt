package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class activity_login : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate((layoutInflater))
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.LogintoSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.LoginEmail.text.toString()
            val pass = binding.loginPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(user.uid)
                            userRef.get().addOnSuccessListener { dataSnapshot ->
                                val userType = dataSnapshot.child("userType").value as String
                                val intent = when (userType) {
                                    "client" -> Intent(this, ClientMainProfile::class.java)
                                    "freelancer" -> Intent(this, FreelancerMainProfile::class.java)
                                    else -> null
                                }
                                if (intent != null) {
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this, "Unknown user type", Toast.LENGTH_SHORT).show()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Empty fields aren't allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
