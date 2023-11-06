package com.example.taskmanager.ui.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.Task

class TaskAdapter(
    private val onLongClick: (Task) -> Unit,
    private val onClick: (Task) -> Unit
) :
    Adapter<TaskAdapter.TaskViewHolder>() {

    private val list = arrayListOf<Task>()

    fun addTasks(tasks: List<Task>) {
        list.clear()
        list.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        holder.bind(list.get(position))
    }


    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : ViewHolder(binding.root) {
        fun bind(task: Task) {

            itemView.setBackgroundColor(if (adapterPosition % 2 == 0 ) Color.BLACK else Color.WHITE)
            binding.tvTitle2.setTextColor(if (adapterPosition % 2 == 0 ) Color.WHITE else Color.BLACK)
            binding.tvDesc2.setTextColor(if (adapterPosition % 2 == 0 ) Color.WHITE else Color.BLACK)

            binding.tvTitle2.text = task.title
            binding.tvDesc2.text = task.desc

            itemView.setOnLongClickListener {
                onLongClick(task)
                false
            }
            itemView.setOnClickListener {
                onClick(task)
            }
        }
    }
}