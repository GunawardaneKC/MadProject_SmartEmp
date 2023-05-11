package com.freelancingapp.freelancingapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freelancingapp.freelancingapp.FreelancerData
import com.freelancingapp.freelancingapp.R

class FreelancerAdapter(private val freelancerList: List<FreelancerData>) :
    RecyclerView.Adapter<FreelancerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstname: TextView = itemView.findViewById(R.id.tv_firstname)
        val lastname: TextView = itemView.findViewById(R.id.tv_lastname)
        val phone_number: TextView = itemView.findViewById(R.id.tv_phone_number)
        val address: TextView = itemView.findViewById(R.id.tv_skills)
        val skills: TextView = itemView.findViewById(R.id.docaddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.free_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFreelancer = freelancerList[position]
        holder.firstname.text = currentFreelancer.firstname
        holder.lastname.text = currentFreelancer.lastname
        holder.phone_number.text = currentFreelancer.phone_number
        holder.address.text = currentFreelancer.address
        holder.skills.text = currentFreelancer.skills
    }

    override fun getItemCount(): Int {
        return freelancerList.size
    }
}
