package com.example.practicaltest.network

import com.example.practicaltest.data.network.model.data_class.ForecastResponse
import com.example.practicaltest.features.weather_info_show.model.data_class.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun callApiForWeatherInfo(@Query("lat") latitude: Double,@Query("lon") longitude: Double): Call<WeatherInfoResponse>

    @GET("forecast")
    fun callApiForWeatherForecast(@Query("lat") latitude: Double,@Query("lon") longitude: Double): Call<ForecastResponse>


}