package com.example.practicaltest.data.database.repository

import androidx.lifecycle.LiveData
import com.example.practicaltest.data.database.PlaceDatabase
import com.example.practicaltest.data.database.dao.PlaceDAO
import com.example.practicaltest.data.database.entity.PlaceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceRepository(placeDatabase: PlaceDatabase) {

    private var placeDAO: PlaceDAO = placeDatabase.getPlaceDAO()

    fun getplaces(): LiveData<List<PlaceEntity>> {
        return placeDAO.getAllPlaces()
    }

    fun insertPlace(placeEntity: PlaceEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            placeDAO.addPlace(placeEntity)
        }
    }


}