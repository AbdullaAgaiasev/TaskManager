package com.example.taskmanager.ui.auth.code

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentCodeBinding
import com.example.taskmanager.ui.auth.phone.PhoneFragment
import com.example.taskmanager.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class CodeFragment : Fragment() {

    private lateinit var binding: FragmentCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val verId = arguments?.getString(PhoneFragment.VER_ID_KEY)

        binding.btnAccept.setOnClickListener {
            val credential =
                PhoneAuthProvider.getCredential(verId!!, binding.etCode.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnSuccessListener {
                showToast(getString(R.string.success_msg))
                findNavController().navigate(R.id.navigation_home)
            }
            .addOnFailureListener {
                showToast(it.message.toString())
            }
    }

}