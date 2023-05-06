package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityClientAuthEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class ClientAuthEdit : AppCompatActivity() {

    private lateinit var binding: ActivityClientAuthEditBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAuthEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!

        val email = currentUser.email
        val username = currentUser.displayName

        binding.saveButton.setOnClickListener {
            val newUsername = binding.editUsername.text.toString().trim()
            if (newUsername.isEmpty()) {
                binding.editUsername.error = "Username is required!"
                binding.editUsername.requestFocus()
                return@setOnClickListener
            }

            val profileUpdates = currentUser
                .updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(newUsername)
                        .build())

            profileUpdates
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Username updated successfully!", Toast.LENGTH_SHORT).show()
                        binding.textView39.text = "Username: $newUsername"
                    } else {
                        Toast.makeText(this, "Failed to update username!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.PassReset.setOnClickListener {
            auth.sendPasswordResetEmail(currentUser.email!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to send password reset email!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.deleteButton.setOnClickListener {
            auth.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.signOut()
                        startActivity(Intent(this@ClientAuthEdit, activity_login::class.java))
                        finish()
                        Toast.makeText(this, "Account deleted successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to delete account!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
