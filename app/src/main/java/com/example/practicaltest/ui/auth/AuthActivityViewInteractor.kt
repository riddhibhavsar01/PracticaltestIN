package com.example.practicaltest.ui.auth

import com.google.firebase.auth.PhoneAuthCredential
import com.example.practicaltest.ui.base.ViewInteractor

interface AuthActivityViewInteractor: ViewInteractor {

    fun showSnackBarMessage(message: String)

    fun goToGoalActivity()

    fun startSMSListener()

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)
}