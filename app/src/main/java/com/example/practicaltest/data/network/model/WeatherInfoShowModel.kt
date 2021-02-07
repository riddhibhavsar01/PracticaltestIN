package com.example.practicaltest.features.weather_info_show.model

import com.example.practicaltest.data.network.common.RequestCompleteListener
import com.example.practicaltest.data.network.model.data_class.ForecastResponse
import com.example.practicaltest.features.weather_info_show.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {

    fun getWeatherInfo(lat: Double,long:Double, callback: RequestCompleteListener<WeatherInfoResponse>)
    fun getWeatherForecast(lat: Double,long:Double, callback: RequestCompleteListener<ForecastResponse>)
}