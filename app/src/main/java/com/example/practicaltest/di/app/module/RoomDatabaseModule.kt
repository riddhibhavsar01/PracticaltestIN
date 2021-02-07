package com.example.practicaltest.di.app.module

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practicaltest.data.database.PlaceDatabase
import com.example.practicaltest.di.app.scope.AppScoped
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class RoomDatabaseModule {

    private lateinit var placeDatabase: PlaceDatabase

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RoomDatabaseModule", "onCreate")
            CoroutineScope(Dispatchers.IO).launch {

            }
        }
    }


    @AppScoped
    @Provides
    fun providesRoomDatabase(application : Application): PlaceDatabase {
        placeDatabase = Room.databaseBuilder(application, PlaceDatabase::class.java, "place_database")
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()
        return placeDatabase
    }

    @AppScoped
    @Provides
    fun providesBookDAO(placeDatabase: PlaceDatabase) = placeDatabase.getPlaceDAO()
}