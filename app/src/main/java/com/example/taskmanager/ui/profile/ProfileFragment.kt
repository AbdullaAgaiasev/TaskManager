package com.example.taskmanager.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentProfileBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.utils.loadImage
import com.example.taskmanager.utils.showToast


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
        binding.etSurname.setText(pref.getSurname())
        binding.profileImage.loadImage(pref.getImage())

        binding.btnSaveProfile.setOnClickListener {
            pref.setName(binding.etName.text.toString())
            pref.setSurname(binding.etSurname.text.toString())
            showToast(getString(R.string.upload_success))
        }

        binding.imageView.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle(getString(R.string.delete_title))
                .setMessage(getString(R.string.delete_message))
                .setNegativeButton(getString(R.string.No)) { dialog, _ -> dialog?.dismiss() }
                .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->
                    findNavController().navigate(R.id.phoneFragment)
                }
            alert.create().show()
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(intent)
        }
    }
}