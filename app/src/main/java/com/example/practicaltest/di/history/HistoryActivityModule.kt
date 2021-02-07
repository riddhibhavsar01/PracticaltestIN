package com.example.practicaltest.di.home

import android.app.Application
import android.content.Context
import com.example.practicaltest.di.app.scope.ActivityScoped
import com.example.practicaltest.ui.history.HistoryActivity
import com.example.practicaltest.ui.splash.SplashActivity
import com.example.practicaltest.utils.BaseResourceProvider
import com.example.practicaltest.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity


@Module
class HistoryActivityModule {

    @Provides
    @ActivityScoped
    fun provideActivityContext(activity: HistoryActivity): Context {
        return activity
    }

    @Provides
    @ActivityScoped
    fun provideActivity(activity: HistoryActivity): DaggerAppCompatActivity {
        return activity
    }

    @Provides
    @ActivityScoped
    fun provideResourceProvider(context: Application): BaseResourceProvider {
        return ResourceProvider(context)
    }
}