package com.example.practicaltest.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.R
import com.example.practicaltest.data.database.entity.PlaceEntity
import com.example.practicaltest.databinding.ListItemPlaceBinding
import com.example.practicaltest.BR

class PlaceAdapter(private var placeList: List<PlaceEntity>?) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_place, parent, false))
    }

    override fun getItemCount(): Int {
        return placeList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.dataBinding.setVariable(BR.place, placeList?.get(position))
    }

    class PlaceViewHolder(binding: ListItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        var dataBinding = binding
    }
}