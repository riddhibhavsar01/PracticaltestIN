package com.example.practicaltest.ui.auth.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practicaltest.ui.auth.fragments.PhoneVerificationFragment
import com.example.practicaltest.ui.weatherinfo.WeatherInfoActivity
import com.example.practicaltest.ui.weatherinfo.fragments.CurrentWeatherFragment
import com.example.practicaltest.ui.weatherinfo.fragments.ForecastFragment

class WeatherPagerAdapter(
   var activity: WeatherInfoActivity
) : FragmentStateAdapter(activity) {

    private var mTabCount = 0

    fun setCount(count: Int) {
        mTabCount = count
    }

   override fun getItemCount(): Int {
        return mTabCount
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrentWeatherFragment.newInstance(activity.getBundle())
            1 -> ForecastFragment.newInstance(activity.getBundle())
            else -> CurrentWeatherFragment.newInstance(activity.getBundle())
        }
    }


}