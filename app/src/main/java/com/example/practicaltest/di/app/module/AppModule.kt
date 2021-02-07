package com.example.practicaltest.di.app.module

import com.google.firebase.auth.FirebaseAuth
import com.example.practicaltest.di.app.scope.AppScoped
import com.example.practicaltest.network.ApiInterface
import com.example.practicaltest.network.RetrofitClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @AppScoped
    internal fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @AppScoped
    fun provideApiService() : ApiInterface {
        return RetrofitClient.client.create(ApiInterface::class.java)
    }



}