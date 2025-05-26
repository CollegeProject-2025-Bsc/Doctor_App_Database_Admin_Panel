package com.example.doctor_app_database_admin_panel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor_app_database_admin_panel.databinding.DepViewBinding

class DepAdapter(private val list: List<DepartmentModel>): RecyclerView.Adapter<DepAdapter.ViewHolder>() {


    private lateinit var context: Context
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        lateinit var name:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dep_view,parent,false))
    }

    override fun onBindViewHolder(holder: DepAdapter.ViewHolder, position: Int) {
        name = holder.itemView.findViewById(R.id.name)
        name.text = list[position].name
        holder.itemView.setOnClickListener{
            context.startActivity(Intent(holder.itemView.context,Doctor::class.java).putExtra("dep",list[position].name).putExtra("id",list[position].id))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}