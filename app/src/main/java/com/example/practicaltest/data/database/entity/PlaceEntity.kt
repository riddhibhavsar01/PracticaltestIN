package com.example.practicaltest.data.database.entity

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    var placeId: Int,
    @ColumnInfo(name = "place_address")
    var placeAddress: String? = null,
    @ColumnInfo(name = "place_latlong")
    var placeLatLong: String? = null
){

}