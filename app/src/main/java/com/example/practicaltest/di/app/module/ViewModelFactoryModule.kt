package com.example.practicaltest.di.app.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicaltest.data.database.repository.PlaceRepository
import com.example.practicaltest.di.app.scope.AppScoped
import com.example.practicaltest.di.app.utils.ViewModelFactory
import com.example.practicaltest.di.app.utils.ViewModelKey
import com.example.practicaltest.features.weather_info_show.model.WeatherInfoShowModel
import com.example.practicaltest.features.weather_info_show.model.WeatherInfoShowModelImpl
import com.example.practicaltest.ui.auth.AuthActivityViewModel
import com.example.practicaltest.ui.history.HistoryActivityViewModel

import com.example.practicaltest.ui.main.MainActivityViewModel
import com.example.practicaltest.ui.splash.SplashActivityViewModel
import com.example.practicaltest.ui.weatherinfo.WeatherInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    @AppScoped
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthActivityViewModel::class)
    abstract fun bindAuthActivityViewModel(authActivityViewModel: AuthActivityViewModel): ViewModel

  @Binds
    @IntoMap
    @ViewModelKey(SplashActivityViewModel::class)
    abstract fun bindSplashActivityViewModel(viewModel: SplashActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherInfoViewModel::class)
    abstract fun bindWeatherInfoActivityViewModel(viewModel: WeatherInfoViewModel): ViewModel

    @Binds
    abstract fun bindModel(weatherInfoShowModelImpl: WeatherInfoShowModelImpl): WeatherInfoShowModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryActivityViewModel::class)
    abstract fun bindHistoryActivityViewModel(viewModel: HistoryActivityViewModel): ViewModel

}
