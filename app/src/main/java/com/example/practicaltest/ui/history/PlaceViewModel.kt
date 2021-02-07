package com.example.practicaltest.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicaltest.data.database.entity.PlaceEntity
import com.example.practicaltest.data.database.repository.PlaceRepository


class PlaceViewModel(private var placeRepository: PlaceRepository) : ViewModel() {

    val isBookNameEmpty = MutableLiveData<Boolean>()
    val isBookPriceEmpty = MutableLiveData<Boolean>()
    val shouldFinishActivity = MutableLiveData<Boolean>()
    var isUpdateBook = false
    var selectedCategoryId: Long? = null
    var bookName: String? = null
    var bookPrice: String? = null

    private fun addNewBook(place: PlaceEntity) {
        placeRepository.insertPlace(place)
    }




}