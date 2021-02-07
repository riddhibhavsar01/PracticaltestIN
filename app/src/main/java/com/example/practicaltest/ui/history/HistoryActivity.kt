package com.example.practicaltest.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.R
import com.example.practicaltest.data.database.entity.PlaceEntity
import com.example.practicaltest.databinding.ActivityHistoryBinding
import com.example.practicaltest.ui.base.BaseActivity
import com.example.practicaltest.ui.history.adapter.PlaceAdapter
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : BaseActivity<ActivityHistoryBinding, HistoryActivityViewModel>()  {

    private var placeAdapter: PlaceAdapter? = null
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, HistoryActivity::class.java)
        }
    }
    override fun getViewModelClass(): Class<HistoryActivityViewModel> = HistoryActivityViewModel::class.java

    override fun layoutId(): Int = R.layout.activity_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getPlaceList().observe(this, Observer {
            showPlaceList(it)
        })
    }

    private fun showPlaceList(placeList: List<PlaceEntity>) {
        if (placeAdapter == null) {
            recycler_view_place.layoutManager = LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
            )
            placeAdapter = PlaceAdapter(placeList)
            recycler_view_place.adapter = placeAdapter
            recycler_view_place.addItemDecoration(
                DividerItemDecoration(
                    recycler_view_place.getContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

        }
    }
}