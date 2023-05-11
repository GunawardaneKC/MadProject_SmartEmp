package com.freelancingapp.freelancingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.freelancingapp.freelancingapp.databinding.ActivitySentMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddedVacancy : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var clientsRef: DatabaseReference
    private lateinit var binding: ActivitySentMessageBinding

    private lateinit var database: FirebaseDatabase
    private lateinit var messageEmailEdit: EditText
    private lateinit var messageContentEdit: EditText
    private lateinit var messageEditBtn: Button
    private lateinit var messageDelBtn: Button
    private lateinit var messageId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySentMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        clientsRef = FirebaseDatabase.getInstance().getReference("vacancies")

        val userId = firebaseAuth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }
        clientsRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val client = snapshot.getValue(message::class.java)
                if (client != null) {
                    binding.messageEmailEdit.text = client.email
                    binding.messageContentEdit.text = client.message

                } else {
                    Toast.makeText(this@AddedVacancy, "Client data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AddedVacancy, "Error retrieving data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        binding.messageEditBtn.setOnClickListener {
            startActivity(Intent(this, AddedVacancyEdit::class.java))
        }
    }
//    companion object {
//        const val TAG = "EditMessageActivity"
//    }

    class message(
        val mid: String? = "",
        val email: String? = "",
        val message: String? = ""
    )
}
