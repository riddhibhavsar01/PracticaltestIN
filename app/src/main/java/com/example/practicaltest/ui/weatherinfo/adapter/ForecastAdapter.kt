package com.example.practicaltest.ui.weatherinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.R

import com.example.practicaltest.BR
import com.example.practicaltest.data.network.model.data_class.ForecastResponse
import com.example.practicaltest.databinding.ListItemForecastBinding

class ForecastAdapter(private var forecastList: List<ForecastResponse.ForecastResponseListItem>?) : RecyclerView.Adapter<ForecastAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_forecast, parent, false))
    }

    override fun getItemCount(): Int {
        return forecastList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.dataBinding.setVariable(BR.forecast, forecastList?.get(position))
    }

    class PlaceViewHolder(binding: ListItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        var dataBinding = binding
    }
}