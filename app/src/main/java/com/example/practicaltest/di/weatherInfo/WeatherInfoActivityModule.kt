package com.example.practicaltest.di.home

import android.app.Application
import android.content.Context
import com.example.practicaltest.di.app.scope.ActivityScoped
import com.example.practicaltest.ui.auth.adapter.AuthPagerAdapter
import com.example.practicaltest.ui.auth.adapter.WeatherPagerAdapter
import com.example.practicaltest.ui.weatherinfo.WeatherInfoActivity
import com.example.practicaltest.utils.BaseResourceProvider
import com.example.practicaltest.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity


@Module
class WeatherInfoActivityModule {

    @Provides
    @ActivityScoped
    fun provideActivityContext(activity: WeatherInfoActivity): Context {
        return activity
    }

    @Provides
    @ActivityScoped
    fun provideActivity(activity: WeatherInfoActivity): DaggerAppCompatActivity {
        return activity
    }

    @Provides
    @ActivityScoped
    fun provideResourceProvider(context: Application): BaseResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @ActivityScoped
    fun provideWeatherAdapter(activity: WeatherInfoActivity): WeatherPagerAdapter {
        return WeatherPagerAdapter(activity)
    }




}