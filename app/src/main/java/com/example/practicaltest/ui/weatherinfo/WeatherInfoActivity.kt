package com.example.practicaltest.ui.weatherinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.practicaltest.R
import com.example.practicaltest.databinding.ActivityWeatherInfoBinding
import com.example.practicaltest.ui.auth.adapter.WeatherPagerAdapter
import com.example.practicaltest.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class WeatherInfoActivity : BaseActivity<ActivityWeatherInfoBinding, WeatherInfoViewModel>(){

    companion object {
        fun getIntent(context: Context,latitude :Double,longitude:Double): Intent {
            val intent =Intent(context, WeatherInfoActivity::class.java)
            intent.putExtra("latitude",latitude)
            intent.putExtra("longitude",longitude)
            return intent
        }
    }

    private val tabTitles = arrayOf("Current Weather", "Forecast")

    @Inject
    lateinit var weatherPagerAdapter: WeatherPagerAdapter
    var latitude : Double = 0.0
    var longitude :Double =0.0
    override fun getViewModelClass(): Class<WeatherInfoViewModel> = WeatherInfoViewModel::class.java

    override fun layoutId(): Int = R.layout.activity_weather_info
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.pagerPagePosition.observe(
            this, Observer<Int?> { value ->
                binding.weatherViewPager.currentItem = value ?: 0
            }
        )
        latitude = intent.extras!!.getDouble("latitude")
        longitude = intent.extras!!.getDouble("longitude")
       setUpPager()

    }

    private fun setUpPager() {
        weatherPagerAdapter.setCount(2)

        binding.weatherViewPager.adapter = weatherPagerAdapter
        binding.weatherViewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.weatherViewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.weatherViewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

       fun getBundle():Bundle{
        var b = Bundle()
        b.putDouble("latitude",latitude)
        b.putDouble("longitude",longitude)
        return b
    }


}