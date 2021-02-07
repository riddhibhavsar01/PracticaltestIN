package com.example.practicaltest.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.practicaltest.R
import com.example.practicaltest.databinding.FragmentPhoneVerificationBinding
import com.example.practicaltest.ui.auth.AuthActivityViewModel
import com.example.practicaltest.ui.base.BaseFragment
import com.example.practicaltest.utils.PreferenceManager


class PhoneVerificationFragment : BaseFragment<FragmentPhoneVerificationBinding, AuthActivityViewModel>() {

    companion object {
        fun newInstance(): PhoneVerificationFragment {
            val args = Bundle()
            val fragment = PhoneVerificationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getViewModelClass(): Class<AuthActivityViewModel> = AuthActivityViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_phone_verification

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedPhoneNumber.observe(
            requireActivity(),
            Observer<String?> { value ->
                binding.textInputEditTextPhone.setText(value ?: "")
            })

        binding.buttonVerifyPhone.setOnClickListener {
            activity?.hideKeyboard()
            if (viewModel.checkIfPhoneIsValid(binding.textInputEditTextPhone.text.toString())) {
                viewModel.sendOtpToPhone(binding.textInputEditTextPhone.text.toString())
                var preferenceManager1 = PreferenceManager(requireActivity())
                preferenceManager1.setStringPreference("mobno",binding.textInputEditTextPhone.text.toString())
            } else {
                binding.textInputLayoutPhone.error = "Invalid Phone: Please enter phone number with country code"
            }
        }
    }
}