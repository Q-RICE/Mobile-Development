package com.c23pc607.q_rice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class ServiceAdapter(private val listService: ArrayList<Service>) : RecyclerView.Adapter<ServiceAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, icon) = listService[position]
        holder.imgIcon.setImageResource(icon)
        holder.tvName.text = name
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listService[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = listService.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgIcon: ImageView = itemView.findViewById(R.id.img_item_icon)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Service)
    }
}