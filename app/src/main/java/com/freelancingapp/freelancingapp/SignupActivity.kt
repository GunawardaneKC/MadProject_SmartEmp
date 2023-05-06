package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.SignUptoLogin.setOnClickListener{
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }

        binding.CreateAccount.setOnClickListener{
            val email = binding.SignUpEmail.text.toString()
            val username = binding.SignUpUsername.text.toString()
            val pass = binding.SignUpPassword.text.toString()
            val confirmPass = binding.SignUpConfirmPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && username.isNotEmpty()){
                if (pass == confirmPass){

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful){

                            // Save user data to Firebase Database
                            val userRef = database.getReference("Users").child(firebaseAuth.currentUser!!.uid)
                            val userData = hashMapOf(
                                "email" to email,
                                "username" to username
                            )
                            userRef.setValue(userData)

                            val intent1 = Intent(this, UserSelect::class.java)
                            startActivity(intent1)

                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this,"Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty fields aren't allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
