package com.example.practicaltest.ui.weatherinfo.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.practicaltest.R
import com.example.practicaltest.databinding.FragmentCurrentWeatherBinding
import com.example.practicaltest.features.weather_info_show.model.data_class.WeatherData
import com.example.practicaltest.ui.base.BaseFragment
import com.example.practicaltest.ui.weatherinfo.WeatherInfoViewModel

import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.layout_sunrise_sunset.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.*


class CurrentWeatherFragment : BaseFragment<FragmentCurrentWeatherBinding, WeatherInfoViewModel>(){



    companion object {
        fun newInstance(args :Bundle): CurrentWeatherFragment {
        //   val args = Bundle()
            val fragment = CurrentWeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getViewModelClass(): Class<WeatherInfoViewModel> = WeatherInfoViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_current_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setLiveDataListeners()

        viewModel.getWeatherInfo(requireArguments().getDouble("latitude"),requireArguments().getDouble("longitude")) // fetch weather info

    }

    private fun setLiveDataListeners() {

        viewModel.progressBarLiveData.observe(requireActivity(), Observer { isShowLoader ->
            if (isShowLoader)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })


        viewModel.weatherInfoLiveData.observe(requireActivity(), Observer { weatherData ->
            setWeatherInfo(weatherData)
        })


        viewModel.weatherInfoFailureLiveData.observe(requireActivity(), Observer { errorMessage ->
            output_group.visibility = View.GONE
            tv_error_message.visibility = View.VISIBLE
            tv_error_message.text = errorMessage
        })
    }


    private fun setWeatherInfo(weatherData: WeatherData) {
        output_group.visibility = View.VISIBLE
        tv_error_message.visibility = View.GONE

        tv_date_time?.text = weatherData.dateTime
        tv_temperature?.text = weatherData.temperature
        tv_city_country?.text = weatherData.cityAndCountry
        tv_humidity_value?.text = weatherData.humidity
        tv_pressure_value?.text = weatherData.pressure
        tv_visibility_value?.text = weatherData.visibility

        tv_sunrise_time?.text = weatherData.sunrise
        tv_sunset_time?.text = weatherData.sunset
    }


}