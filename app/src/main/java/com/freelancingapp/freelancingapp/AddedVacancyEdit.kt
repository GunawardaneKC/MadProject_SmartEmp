package com.freelancingapp.freelancingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivitySendMessageEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class msg(
//    val mid: String? = "",
    val email: String? = "",
    val message: String? = ""
)

class AddedVacancyEdit : AppCompatActivity() {

    private lateinit var binding: ActivitySendMessageEditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var clientsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySendMessageEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        clientsRef = FirebaseDatabase.getInstance().getReference("vacancies")

        val userId = firebaseAuth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }
        clientsRef.child(userId).get().addOnSuccessListener { snapshot ->
            val client = snapshot.getValue(msg::class.java)
            if (client != null) {
                binding.messageEmailEdit.setText(client.email)
                binding.messageContentEdit.setText(client.message)
            } else {
                Toast.makeText(this, "Client data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error retrieving data: ${it.message}", Toast.LENGTH_SHORT).show()
        }

        binding.messageEditBtn.setOnClickListener {
            val updatedClient = msg(
                binding.messageEmailEdit.text.toString(),
                binding.messageContentEdit.text.toString()
            )

            clientsRef.child(userId).setValue(updatedClient).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Client details updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error updating client details: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.messageDelBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to delete your account information?")
            builder.setPositiveButton("Yes") { _, _ ->
                clientsRef.child(userId).removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account information deleted successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error deleting account information: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.create().show()
        }

    }
}