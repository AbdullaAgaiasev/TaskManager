package com.example.taskmanager.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.Cinema

class CinemaAdapter : Adapter<CinemaAdapter.CinemaViewHolder>() {

    private val list = arrayListOf<Cinema>()

    fun addList(newList: List<Cinema>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        return CinemaViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.bind(list.get(position))
    }

    inner class CinemaViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root){

        fun bind(cinema: Cinema) {
            binding.tvTitle2.text = cinema.name
            binding.tvDesc2.text = cinema.author
        }

    }

}