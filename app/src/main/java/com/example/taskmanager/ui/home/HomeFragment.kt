package com.example.taskmanager.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.home.adapter.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapter = TaskAdapter(this::onLongClick)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun onLongClick(task: Task) {

        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Alert!!")
        alert.setMessage("Are you sure to delete record")
        alert.setNegativeButton("NO") { dialog, _ ->
            dialog?.dismiss() }

        alert.setPositiveButton("YES") { dialog, _ ->
            dialog?.dismiss()

            App.db.taskDao().delete(task)
            val tasks = App.db.taskDao().getAll()
            adapter.addTasks(tasks)
        }

        alert.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}