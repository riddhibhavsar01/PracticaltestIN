package com.example.practicaltest.ui.auth

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.PhoneAuthProvider
import com.example.practicaltest.ui.base.BaseViewModel
import com.example.practicaltest.utils.FireBaseAuthProvider
import com.example.practicaltest.utils.PhoneCallbacksListener
import timber.log.Timber
import javax.inject.Inject


class AuthActivityViewModel @Inject constructor(val fireBaseAuthProvider: FireBaseAuthProvider) :
    BaseViewModel<AuthActivityViewInteractor>(), PhoneCallbacksListener {

    companion object {
        val PHONE_VERIFICATION_PAGE = 0
        val OTP_VERIFICATION_PAGE = 1
        private const val RESEND_WAIT_MILLIS: Long = 30000
        private const val TICK_INTERVAL_MILLIS: Long = 1000

    }

    init {
        fireBaseAuthProvider.setPhoneCallbacksListener(this)
        if (fireBaseAuthProvider.isUserVerified()) {
            viewInteractor?.goToGoalActivity()
        }
    }

    var selectedPhoneNumber = MutableLiveData<String>()
    var selectedOtpNumber = MutableLiveData<String>()

    var pagerPagePosition = MutableLiveData<Int>()

    var showResendCodeText = MutableLiveData<String>()
    var timer = MutableLiveData<String>()
    var phone:String = ""

    fun sendOtpToPhone(phone: String) {
        this.phone = phone
        viewInteractor?.startSMSListener()
        fireBaseAuthProvider.sendVerificationCode(phone)
    }

    fun verifyOtp(otp: String) {
        viewInteractor?.signInWithPhoneAuthCredential(fireBaseAuthProvider.verifyVerificationCode(otp))
    }



    fun checkIfPhoneIsValid(phone: String): Boolean {
        return phone.let {
            !it.isBlank() && (it.length > 10)
        }
    }

    fun checkIfOtpIsValid(otp: String): Boolean {
        return otp.let {
            !it.isBlank() && (it.length == 6)
        }
    }


    override fun onVerificationCompleted() {
        viewInteractor?.showSnackBarMessage("Verification Completed")
        viewInteractor?.goToGoalActivity()
    }

    override fun onVerificationCodeDetected(code: String) {
        Timber.d("AuthActivityViewModel onReceive: success $code")
        selectedOtpNumber.value = code
    }

    override fun onVerificationFailed(message: String) {
        Timber.d(message)
        viewInteractor?.showSnackBarMessage(message)
    }

    override fun onCodeSent(
        verificationId: String?,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        viewInteractor?.showSnackBarMessage("OTP has sent")
        pagerPagePosition.value = OTP_VERIFICATION_PAGE
    }
}