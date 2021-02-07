package com.example.practicaltest.ui.auth.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.Observer
import com.example.practicaltest.R
import com.example.practicaltest.databinding.FragmentOtpVerificationBinding
import com.example.practicaltest.ui.auth.AuthActivityViewModel
import com.example.practicaltest.ui.base.BaseFragment


class OtpVerificationFragment: BaseFragment<FragmentOtpVerificationBinding, AuthActivityViewModel>() {

    companion object {
        fun newInstance(): OtpVerificationFragment {
            val args = Bundle()
            val fragment = OtpVerificationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getViewModelClass(): Class<AuthActivityViewModel> = AuthActivityViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_otp_verification

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val text: String = resources.getString(R.string.otp_auth_subtitle, viewModel.phone)
        binding.textViewSubtitleOtpAuth.text = text

        counterStart()

        viewModel.selectedOtpNumber.observe(
            requireActivity(),
            Observer<String?> { value ->
                binding.otpEditText.setText(value ?: "")
            }
        )

        binding.buttonVerifyOtp.setOnClickListener {
            if (viewModel.checkIfOtpIsValid(binding.otpEditText.text.toString())) {
                viewModel.verifyOtp(binding.otpEditText.text.toString())
            } else {
                showSnackBar("Invalid Otp: Please enter correct OTP")
            }
        }

        viewModel.timer.observe(
            requireActivity(),
            Observer<String?> { value ->
                value?.let { binding.textViewTicker!!.text = value }

            }
        )




    }

    fun counterStart(){
        object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds = seconds % 60

                viewModel.timer.postValue( String.format("%02d", minutes) + ":" + String.format(
                    "%02d",
                    seconds
                ))
            }

            override fun onFinish() {
               viewModel.timer.postValue("00:00")
            }
        }.start()
    }


}