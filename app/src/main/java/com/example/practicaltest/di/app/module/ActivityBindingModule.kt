package com.example.practicaltest.di.app.module

import com.example.practicaltest.di.app.scope.ActivityScoped
import com.example.practicaltest.di.auth.AuthActivityModule
import com.example.practicaltest.di.auth.FragmentBindingModule
import com.example.practicaltest.di.home.*
import com.example.practicaltest.di.weatherInfo.FragmentWeatherBindingModule
import com.example.practicaltest.ui.auth.AuthActivity
import com.example.practicaltest.ui.history.HistoryActivity

import com.example.practicaltest.ui.main.MainActivity
import com.example.practicaltest.ui.splash.SplashActivity
import com.example.practicaltest.ui.weatherinfo.WeatherInfoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [AuthActivityModule::class, FragmentBindingModule::class])
    internal abstract fun bindAuthActivity(): AuthActivity

  @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun bindSplashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [WeatherInfoActivityModule::class, FragmentWeatherBindingModule::class])
    internal abstract fun bindWeatherActivity(): WeatherInfoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HistoryActivityModule::class])
    internal abstract fun bindHistoryActivity(): HistoryActivity
}