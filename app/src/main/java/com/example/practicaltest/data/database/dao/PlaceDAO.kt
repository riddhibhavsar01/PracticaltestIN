package com.example.practicaltest.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.practicaltest.data.database.entity.PlaceEntity


@Dao
interface PlaceDAO {
    @Insert
  suspend  fun addPlace(book: PlaceEntity) : Long

    @Query("SELECT * FROM places")
    fun getAllPlaces() : LiveData<List<PlaceEntity>>

}