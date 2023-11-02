package com.example.taskmanager.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
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

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        holder.bind(list.get(position))

        if (position % 2 == 0) {
            // Четная позиция, устанавливаем черный цвет фона
            holder.itemView.setBackgroundResource(R.color.black)
            holder.textView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.descTextView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            ) } else {
            // Нечетная позиция, устанавливаем белый цвет фона
            holder.itemView.setBackgroundResource(R.color.white)
        }
    }


    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : ViewHolder(binding.root) {
        val textView: TextView = itemView.findViewById(R.id.tv_title2)
        val descTextView: TextView = itemView.findViewById(R.id.tv_desc2)
        fun bind(task: Task) {
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