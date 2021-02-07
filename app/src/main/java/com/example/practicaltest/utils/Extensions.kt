package com.example.practicaltest.utils

import android.app.ProgressDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import com.example.practicaltest.R
import java.text.SimpleDateFormat
import java.util.*


fun Int.unixTimestampToDateTimeString() : String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this*1000.toLong()

        val outputDateFormat = SimpleDateFormat("dd MMM, yyyy - hh:mm a", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault() // user's default time zone
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}

fun Int.unixTimestampToTimeString() : String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this*1000.toLong()

        val outputDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}


/**
 * The temperature T in degrees Celsius (°C) is equal to the temperature T in Kelvin (K) minus 273.15:
 * T(°C) = T(K) - 273.15
 *
 * Example
 * Convert 300 Kelvin to degrees Celsius:
 * T(°C) = 300K - 273.15 = 26.85 °C
 */
fun Double.kelvinToCelsius() : String {

    return  (this - 273.15).toInt().toString()
}

fun Context.getAddress(latitude: Double, longitude: Double) : String{
    val geocoder: Geocoder
    val addresses: List<Address>
    geocoder = Geocoder(this, Locale.getDefault())

    addresses = geocoder.getFromLocation(
        latitude,
        longitude,
        1
    )

    val address: String? =
        addresses[0].getAddressLine(0)
    val city: String? = addresses[0].getLocality()
    val state: String? = addresses[0].getAdminArea()
    val country: String? = addresses[0].getCountryName()
    val knownName: String? = addresses[0].getFeatureName()
    return knownName+"\n"+address+"\n"+city+"\n"+state+"\n"+country
}

/**
 * show progress dialog
 */

fun getProgressDialog(mContext: Context): ProgressDialog {
    val dialog = ProgressDialog(mContext)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    dialog.isIndeterminate = true
    dialog.setMessage("Please wait")
    return dialog
}

/**
 * show toast messages
 */
fun showMessage(mContext: Context?, message: String?) {
    if (mContext != null && message != null && !message.trim().isEmpty()) {
        Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show()
    }
}