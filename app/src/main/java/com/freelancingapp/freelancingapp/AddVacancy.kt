package com.freelancingapp.freelancingapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class message(
    val mid: String? =  null,
    val email: String? = null,
    val message: String? = null
)

class AddVacancy : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etdname:EditText
    private lateinit var etdemail:EditText
    private lateinit var dregbtn:Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)


        auth= FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("vacancies")

        etdname = findViewById(R.id.messageEmail)
        etdemail = findViewById(R.id.messageContent)
        dregbtn = findViewById(R.id.messageSaveBtn)

        val currentUser = auth.currentUser

        dregbtn.setOnClickListener {
            saveDoctorData()

        }
    }

    private fun saveDoctorData() {
        val docName = etdname.text.toString()
        val docEmail = etdemail.text.toString()

        if (docName.isEmpty()) {
            etdname.error = "Please enter name"
        }
        if (docEmail.isEmpty()) {
            etdemail.error = "Please enter Email"
        }else {
            val userId = auth.currentUser?.uid

            if (userId == null) {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
                return
            }

            val doctors = message(userId, docName, docEmail)

            dbRef.child(userId).setValue(doctors)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    etdname.text.clear()
                    etdemail.text.clear()

                }
                .addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }


        }
    }
}