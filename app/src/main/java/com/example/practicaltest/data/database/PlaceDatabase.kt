package com.example.practicaltest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicaltest.data.database.dao.PlaceDAO
import com.example.practicaltest.data.database.entity.PlaceEntity


@Database(entities = [PlaceEntity::class], version = 1,exportSchema = false)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun getPlaceDAO(): PlaceDAO
}