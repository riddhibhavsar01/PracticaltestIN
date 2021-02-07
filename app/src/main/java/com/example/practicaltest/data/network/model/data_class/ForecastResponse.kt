package com.example.practicaltest.data.network.model.data_class

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("city")
    val city: ForecastResponseCity,
    @SerializedName("list")
    val list: List<ForecastResponseListItem>
) {

    data class ForecastResponseCity (
        @SerializedName("name")
        val name: String
    )

    data class ForecastResponseListItem(
        @SerializedName("dt")
        val date: Int,
        @SerializedName("main")
        val main: ForecastResponseListItemMain,
        @SerializedName("weather")
        val weather: List<ForecastResponseListItemWeather>
    ) {

        data class ForecastResponseListItemMain(
            @SerializedName("temp")
            val temperature: Double,
            @SerializedName("feels_like")
            val feelsLike: Double,
            @SerializedName("humidity")
            val Humidity: Double

        )

        data class ForecastResponseListItemWeather(
            @SerializedName("main")
            val head: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("icon")
            val image: String
        )
    }
}