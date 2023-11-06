package com.example.taskmanager.ui.auth.phone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneFragment : Fragment() {

    private lateinit var binding: FragmentPhoneBinding

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {}

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            findNavController().navigate(R.id.codeFragment, bundleOf(VER_ID_KEY to verificationId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNumberEditText = view.findViewById<EditText>(R.id.et_phone)

        phoneNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ничего не делаем перед изменением текста.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Проверяем, содержит ли текст фиксированное число перед номером.
                val fixedNumber = "+996"
                if (!s.toString().startsWith(fixedNumber)) {
                    phoneNumberEditText.setText(fixedNumber)
                    phoneNumberEditText.setSelection(fixedNumber.length) // Устанавливаем курсор в конец текста.
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Ничего не делаем после изменения текста.
            }
        })


        binding.btnSend.setOnClickListener {
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(binding.etPhone.text.toString()) // Phone number to verify
                .setTimeout(10L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    }

    companion object {
        const val VER_ID_KEY = "ver.id.key"
    }

}