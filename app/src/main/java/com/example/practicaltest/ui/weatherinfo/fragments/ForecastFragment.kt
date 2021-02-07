package com.example.practicaltest.ui.weatherinfo.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.R
import com.example.practicaltest.data.network.model.data_class.ForecastResponse
import com.example.practicaltest.databinding.FragmentForecastBinding
import com.example.practicaltest.ui.base.BaseFragment
import com.example.practicaltest.ui.weatherinfo.WeatherInfoViewModel
import com.example.practicaltest.ui.weatherinfo.adapter.ForecastAdapter
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.fragment_current_weather.tv_error_message
import kotlinx.android.synthetic.main.fragment_forecast.*


class ForecastFragment: BaseFragment<FragmentForecastBinding, WeatherInfoViewModel>() {

    private var forecastAdapter: ForecastAdapter? = null
    companion object {
        fun newInstance(args : Bundle): ForecastFragment {
            val fragment = ForecastFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getViewModelClass(): Class<WeatherInfoViewModel> = WeatherInfoViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_forecast

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWeatherForecast(requireArguments().getDouble("latitude"),requireArguments().getDouble("longitude")) // fetch weather info
      setLiveDataListeners()
    }

    private fun setLiveDataListeners() {

        viewModel.progressBarLiveData.observe(requireActivity(), Observer { isShowLoader ->
            if (isShowLoader)
                progressBarF.visibility = View.VISIBLE
            else
                progressBarF.visibility = View.GONE
        })


        viewModel.weatherForecastLiveData.observe(requireActivity(), Observer { it ->
            setPlaceInfo(it)
        })


        viewModel.forecastInfoFailureLiveData.observe(requireActivity(), Observer { errorMessage ->
            rvForecast.visibility = View.GONE
            tv_error_message.visibility = View.VISIBLE
            tv_error_message.text = errorMessage
        })
    }
    private fun setPlaceInfo(list: List<ForecastResponse.ForecastResponseListItem>) {
        if (forecastAdapter == null) {
            rvForecast.layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL,
                false
            )
            forecastAdapter = ForecastAdapter(list)
            rvForecast.adapter = forecastAdapter
            rvForecast.addItemDecoration(
                DividerItemDecoration(
                    rvForecast.getContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

        }
    }


}