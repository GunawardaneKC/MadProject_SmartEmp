package com.freelancingapp.freelancingapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freelancingapp.freelancingapp.OrderData
import com.freelancingapp.freelancingapp.R

class OrderAdapter (private val orderList: List<OrderData>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectname: TextView = itemView.findViewById(R.id.tv_firstname)
        val timePeriod: TextView = itemView.findViewById(R.id.tv_lastname)
        val goals: TextView = itemView.findViewById(R.id.tv_phone_number)
        val description: TextView = itemView.findViewById(R.id.tv_skills)
        val orderEmail: TextView = itemView.findViewById(R.id.docaddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFreelancer = orderList[position]
        holder.projectname.text = currentFreelancer.projectname
        holder.timePeriod.text = currentFreelancer.timePeriod
        holder.goals.text = currentFreelancer.goals
        holder.description.text = currentFreelancer.description
        holder.orderEmail.text = currentFreelancer.orderEmail
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}