package com.example.practicaltest.ui.weatherinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicaltest.data.network.common.RequestCompleteListener
import com.example.practicaltest.data.network.model.data_class.ForecastResponse
import com.example.practicaltest.features.weather_info_show.model.WeatherInfoShowModel
import com.example.practicaltest.features.weather_info_show.model.data_class.WeatherData
import com.example.practicaltest.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.example.practicaltest.utils.kelvinToCelsius
import com.example.practicaltest.utils.unixTimestampToDateTimeString
import com.example.practicaltest.utils.unixTimestampToTimeString
import timber.log.Timber
import javax.inject.Inject

class WeatherInfoViewModel @Inject constructor(var model : WeatherInfoShowModel): ViewModel() {
    //@Inject lateinit var onlineCheckerImpl: ConnectionProviderManager

    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherForecastLiveData = MutableLiveData<List<ForecastResponse.ForecastResponseListItem>>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val forecastInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    var pagerPagePosition = MutableLiveData<Int>()
    override fun onCleared() {
        super.onCleared()
        Timber.d("unsubscribeFromDataStore()")
    }

    fun getWeatherInfo(lat: Double,long : Double) {

        progressBarLiveData.postValue(true) // PUSH data to LiveData object to show progress bar

        model.getWeatherInfo(lat,long, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                // business logic and data manipulation tasks should be done here
                val weatherData = WeatherData(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                progressBarLiveData.postValue(false) // PUSH data to LiveData object to hide progress bar

                // After applying business logic and data manipulation, we push data to show on UI
                weatherInfoLiveData.postValue(weatherData) // PUSH data to LiveData object
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false) // hide progress bar
                weatherInfoFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
            }
        })
    }

    fun getWeatherForecast(lat: Double,long : Double) {

        progressBarLiveData.postValue(true) // PUSH data to LiveData object to show progress bar

        model.getWeatherForecast(lat,long, object :
            RequestCompleteListener<ForecastResponse> {
            override fun onRequestSuccess(data: ForecastResponse) {

                // business logic and data manipulation tasks should be done here
                progressBarLiveData.postValue(false) // PUSH data to LiveData object to hide progress bar

                // After applying business logic and data manipulation, we push data to show on UI
                weatherForecastLiveData.postValue( data.list) // PUSH data to LiveData object
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false) // hide progress bar
                forecastInfoFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
            }
        })
    }

}