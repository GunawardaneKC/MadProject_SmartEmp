package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityOrderFormReadBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderFormRead : AppCompatActivity() {
    private lateinit var binding: ActivityOrderFormReadBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var ordersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderFormReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        ordersRef = FirebaseDatabase.getInstance().getReference("jobs")

        val userId = firebaseAuth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        ordersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderData = snapshot.getValue(OrderData::class.java)
                if (orderData != null) {
                    binding.freelancerFirstName.text = "Project name: ${orderData.projectname}"
                    binding.freelancerLastName.text = "Time period: ${orderData.timePeriod}"
                    binding.freelancerPhone.text = "Goals: ${orderData.goals}"
                    binding.freelancerAddress.text = "Description: ${orderData.description}"
                    binding.freelancerSkills.text = "Email: ${orderData.orderEmail}"
                } else {
                    Toast.makeText(this@OrderFormRead, "Order data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@OrderFormRead, "Error retrieving data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.freelancerFormedit.setOnClickListener {
            startActivity(Intent(this, OrderEdit::class.java))
        }
    }
    class OrderData(
        val projectname: String? = "",
        val timePeriod: String? = "",
        val goals: String? = "",
        val description: String? = "",
        val orderEmail: String? = ""
    )
}
