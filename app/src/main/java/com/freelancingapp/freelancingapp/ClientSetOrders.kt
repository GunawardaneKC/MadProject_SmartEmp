package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivityClientSetOrdersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.pow
import kotlin.math.round

class ClientSetOrders : AppCompatActivity() {
    private lateinit var binding: ActivityClientSetOrdersBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientSetOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ordersave.setOnClickListener {

            val userId = firebaseAuth.currentUser?.uid

            if (userId != null) {

                val projectname = binding.pojectname.text.toString()
                val timePeriod = binding.timePeriod.text.toString().toIntOrNull()

                if (timePeriod != null && timePeriod <= 10) {
                    val result = calculatePrice(timePeriod.toString())
                    binding.orderPrice.text = "Rs.%.2f".format(result)
                } else {
                    Toast.makeText(this, "Please enter a valid time period (1-10)", Toast.LENGTH_SHORT).show()
                }

                val goals = binding.goals.text.toString()
                val description = binding.description.text.toString()
                val orderEmail = binding.OrderEmail.text.toString()

                val orderData = OrderData(projectname,
                    timePeriod.toString(), goals, description, orderEmail)
                createOrder(userId, orderData)

            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        }


        binding.calPrice.setOnClickListener {
            val timePeriod = binding.timePeriod.text.toString()
            val result = calculatePrice(timePeriod)

            binding.orderPrice.text = "Rs.$result"
        }
    }

    private fun calculatePrice(timePeriod: String): Double {
        val timePeriodInt = timePeriod.toIntOrNull() ?: 0

        if (timePeriodInt in 1..10) {
            var result = (1.0 / (2.0.pow(timePeriodInt))) * 1000000
            result = round(result / 100) * 100
            binding.orderPrice.text = "Rs.%.2f".format(result)
            return result
        } else {
            Toast.makeText(this, "Maximum time period is 10 days", Toast.LENGTH_LONG).show()
            return 0.0
        }
    }

    private fun createOrder(userId: String, orderData: OrderData) {
        val orderRef = FirebaseDatabase.getInstance().getReference("jobs")

        orderRef.child(userId).setValue(orderData)
            .addOnSuccessListener {
                Toast.makeText(this, "Order created successfully", Toast.LENGTH_SHORT).show()
                val intent1 = Intent(this, ClientMainProfile::class.java)
                startActivity(intent1)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error creating profile", Toast.LENGTH_LONG).show()
            }
            .addOnCompleteListener {

            }
    }
}

