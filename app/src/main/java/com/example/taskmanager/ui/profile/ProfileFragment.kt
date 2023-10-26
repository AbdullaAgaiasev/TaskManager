package com.example.taskmanager.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentProfileBinding
import com.example.taskmanager.utils.loadImage


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val pref by lazy {
        Pref(requireContext())
    }

    private val launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK
            && result.data != null
        ) {
            val photoUri = result.data?.data
            pref.saveImage(photoUri.toString())
            binding.profileImage.loadImage(photoUri.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.setText(pref.getName())
        binding.profileImage.loadImage(pref.getImage())

        binding.btnSaveProfile.setOnClickListener {
            pref.setName(binding.etName.text.toString())
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(intent)
        }
    }
}