package com.freelancingapp.freelancingapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freelancingapp.freelancingapp.Adapter.FreelancerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FreelancerFetch : AppCompatActivity() {
    private lateinit var FreeRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var freeList: ArrayList<FreelancerData>
    private lateinit var adapter: FreelancerAdapter
    private lateinit var dbRef: DatabaseReference
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freelancer_fetch)

        FreeRecyclerView = findViewById(R.id.FreeRecyclerView)
        FreeRecyclerView.layoutManager = LinearLayoutManager(this)
        FreeRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        freeList = arrayListOf<FreelancerData>()

        getFreelancers()
    }

    private fun getFreelancers() {
        FreeRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("freelancers")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                freeList.clear()
                if (snapshot.exists()) {
                    for (docSnap in snapshot.children) {
                        val docData = docSnap.getValue(FreelancerData::class.java)
                        freeList.add(docData!!)
                    }
                    val mAdapter = FreelancerAdapter(freeList)
                    FreeRecyclerView.adapter = mAdapter
                    
                    FreeRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}