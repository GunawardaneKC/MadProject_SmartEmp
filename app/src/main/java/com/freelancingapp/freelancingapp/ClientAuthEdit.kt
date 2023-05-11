package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityClientAuthEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class UserC(
    val username : String = "",
    val email : String = ""
)

class ClientAuthEdit : AppCompatActivity() {

    private lateinit var binding: ActivityClientAuthEditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientAuthEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        userRef = FirebaseDatabase.getInstance().getReference("Users")

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val email = currentUser.email
            val uid = currentUser.uid

            binding.viewEmail.text = email

            userRef.child(uid).get().addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(UserC::class.java)
                if (user != null) {
                    binding.editUsername.setText(user.username)
                    binding.viewEmail.setText(user.email)
                } else {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error retrieving data: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.saveButton.setOnClickListener {
                val updatedUser = UserC(
                    binding.editUsername.text.toString(),
                    binding.viewEmail.text.toString()
                )

                userRef.child(uid).setValue(updatedUser).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Client details updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Error updating client details: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            binding.deleteButton.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to delete your account information?")
                builder.setPositiveButton("Yes") { _, _ ->
                    currentUser.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            userRef.child(uid).removeValue().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Account information deleted successfully", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@ClientAuthEdit, activity_login::class.java))
                                } else {
                                    Toast.makeText(
                                        this,
                                        "${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this@ClientAuthEdit, activity_login::class.java))
                                }
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Error deleting account information: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@ClientAuthEdit, activity_login::class.java))
                        }
                    }
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.create().show()
            }
        }
    }
}
