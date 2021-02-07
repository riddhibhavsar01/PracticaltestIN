package com.example.practicaltest.ui.splash

import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class SplashActivityViewModel @Inject constructor(): ViewModel() {
    //@Inject lateinit var onlineCheckerImpl: ConnectionProviderManager

    override fun onCleared() {
        super.onCleared()
        Timber.d("unsubscribeFromDataStore()")
    }
}