package com.example.practicaltest.ui.main

import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(): ViewModel() {
    //@Inject lateinit var onlineCheckerImpl: ConnectionProviderManager

    override fun onCleared() {
        super.onCleared()
        Timber.d("unsubscribeFromDataStore()")
    }
}