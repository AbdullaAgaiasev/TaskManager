package com.example.taskmanager.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentNotificationsBinding
import com.example.taskmanager.model.Cinema
import com.example.taskmanager.utils.showToast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCinema.setOnClickListener {
            val cinema = Cinema(
                name = binding.etName.text.toString(),
                author = binding.etAuthor.text.toString()
            )
            Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .add(cinema)
                .addOnSuccessListener {
                    showToast(getString(R.string.upload_success))
                    binding.etName.text?.clear()
                    binding.etAuthor.text.clear()
                }
                .addOnFailureListener {
                    showToast(it.message.toString())
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}