package com.freelancingapp.freelancingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityOrderEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OrderD(
    val projectname: String = "",
    val timePeriod: String = "",
    val goals: String = "",
    val description: String = "",
    val orderEmail: String = ""
)

class OrderEdit : AppCompatActivity() {
    private lateinit var binding: ActivityOrderEditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var clientsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        clientsRef = FirebaseDatabase.getInstance().getReference("jobs")

        val userId = firebaseAuth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        clientsRef.child(userId).get().addOnSuccessListener { snapshot ->
            val client = snapshot.getValue(OrderD::class.java)
            if (client != null) {
                binding.editTextFirstName.setText(client.projectname)
                binding.editTextLastName.setText(client.timePeriod)
                binding.editTextCompany.setText(client.goals)
                binding.editTextAddress.setText(client.description)
                binding.editTextPhone.setText(client.orderEmail)
            } else {
                Toast.makeText(this, "Client data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error retrieving data: ${it.message}", Toast.LENGTH_SHORT).show()
        }
        binding.SaveDetail.setOnClickListener {
            val updatedClient = OrderD(
                binding.editTextFirstName.text.toString(),
                binding.editTextLastName.text.toString(),
                binding.editTextCompany.text.toString(),
                binding.editTextPhone.text.toString(),
                binding.editTextAddress.text.toString(),
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


        binding.DeleteDetail.setOnClickListener {
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