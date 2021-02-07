package com.example.practicaltest.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practicaltest.data.database.entity.PlaceEntity
import com.example.practicaltest.data.database.repository.PlaceRepository
import timber.log.Timber
import javax.inject.Inject

class HistoryActivityViewModel @Inject constructor(val placeRepository: PlaceRepository): ViewModel() {
    //@Inject lateinit var onlineCheckerImpl: ConnectionProviderManager

    override fun onCleared() {
        super.onCleared()
        Timber.d("unsubscribeFromDataStore()")
    }

    fun getPlaceList() : LiveData<List<PlaceEntity>> {
        return placeRepository.getplaces()
    }

}