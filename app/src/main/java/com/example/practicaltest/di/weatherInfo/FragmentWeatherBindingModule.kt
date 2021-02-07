package com.example.practicaltest.di.weatherInfo

import androidx.lifecycle.ViewModelProvider
import com.example.practicaltest.di.app.scope.FragmentScoped
import com.example.practicaltest.features.weather_info_show.model.WeatherInfoShowModel
import com.example.practicaltest.features.weather_info_show.model.WeatherInfoShowModelImpl
import com.example.practicaltest.ui.auth.fragments.OtpVerificationFragment
import com.example.practicaltest.ui.auth.fragments.PhoneVerificationFragment
import com.example.practicaltest.ui.weatherinfo.fragments.CurrentWeatherFragment
import com.example.practicaltest.ui.weatherinfo.fragments.ForecastFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentWeatherBindingModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindCurrentWeatherFragment(): CurrentWeatherFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindForecastFragment(): ForecastFragment

}