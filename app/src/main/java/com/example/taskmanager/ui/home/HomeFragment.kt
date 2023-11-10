package com.example.taskmanager.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.home.adapter.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapter = TaskAdapter(this::onLongClick,this::onClick)
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
        setData()
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun onClick(task: Task){
        findNavController().navigate(R.id.taskFragment, bundleOf(TASK_DATA to task))
    }

    private fun onLongClick(task: Task) {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(getString(R.string.delete_title))
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(getString(R.string.No)) { dialog, _ -> dialog?.dismiss() }
            .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->
                dialog?.dismiss()
                App.db.taskDao().delete(task)
                setData()
            }
        alert.create().show()
    }

    private fun setData(){
        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TASK_DATA = "task.data"
    }

}