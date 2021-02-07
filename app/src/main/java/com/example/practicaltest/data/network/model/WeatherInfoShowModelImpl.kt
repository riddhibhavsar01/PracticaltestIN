package com.example.practicaltest.features.weather_info_show.model

import android.content.Context
import com.example.practicaltest.data.network.common.RequestCompleteListener
import com.example.practicaltest.data.network.model.data_class.ForecastResponse
import com.example.practicaltest.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.example.practicaltest.network.ApiInterface


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class WeatherInfoShowModelImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : WeatherInfoShowModel {

    override fun getWeatherInfo(
        latitude: Double,
        longitude: Double,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {

        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfo(latitude,longitude)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(requireNotNull(response.body())) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(requireNotNull(t.localizedMessage)) //let presenter know about failure
            }
        })
    }

    override fun getWeatherForecast(
        lat: Double,
        long: Double,
        callback: RequestCompleteListener<ForecastResponse>
    ) {

         val call: Call<ForecastResponse> = apiInterface.callApiForWeatherForecast(lat,long)

        call.enqueue(object : Callback<ForecastResponse> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(requireNotNull(response.body())) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                callback.onRequestFailed(requireNotNull(t.localizedMessage)) //let presenter know about failure
            }
        })
    }

}