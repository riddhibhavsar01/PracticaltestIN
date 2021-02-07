package com.example.practicaltest.di.app.module

import com.example.practicaltest.data.database.PlaceDatabase
import com.example.practicaltest.data.database.repository.PlaceRepository
import com.example.practicaltest.di.app.scope.AppScoped
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlaceModule {

    @AppScoped
    @Provides
    fun providesPlaceRepository(placeRepository: PlaceDatabase): PlaceRepository {
        return PlaceRepository(placeRepository)
    }

}