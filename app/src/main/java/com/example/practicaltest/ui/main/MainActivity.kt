package com.example.practicaltest.ui.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration

import com.example.practicaltest.R
import com.example.practicaltest.data.database.entity.PlaceEntity
import com.example.practicaltest.data.database.repository.PlaceRepository
import com.example.practicaltest.databinding.ActivityMainBinding
import com.example.practicaltest.ui.auth.AuthActivity
import com.example.practicaltest.ui.base.BaseActivity
import com.example.practicaltest.ui.history.HistoryActivity
import com.example.practicaltest.ui.weatherinfo.WeatherInfoActivity
import com.example.practicaltest.utils.LocationHelper
import com.example.practicaltest.utils.PreferenceManager
import com.example.practicaltest.utils.getAddress
import com.example.practicaltest.utils.showMessage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject


class MainActivity: BaseActivity<ActivityMainBinding, MainActivityViewModel>() ,LocationHelper.LocationHelperCallback, OnMapReadyCallback {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
    override fun getViewModelClass(): Class<MainActivityViewModel> = MainActivityViewModel::class.java
    private var locationHelper: LocationHelper? = null
    override fun layoutId(): Int = R.layout.activity_main

     var latitude: Double=0.0
     var longitude: Double=0.0
    private lateinit var appBarConfiguration: AppBarConfiguration
   lateinit var mMap : GoogleMap
    @Inject
    lateinit var placeRepository : PlaceRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getLocation()
        var preferenceManager= PreferenceManager(this)

        binding.navView.menu.getItem(0).title = "Mobile no : ${ preferenceManager.getStringPreference("mobno")}"

        binding.navView.menu.getItem(1).setOnMenuItemClickListener {

            startActivity(HistoryActivity.getIntent(this))
            true
        }
        binding.navView.menu.getItem(2).setOnMenuItemClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("")
            builder.setMessage("Are you sure want to logout?")
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                FirebaseAuth.getInstance().signOut()
                //  startActivity(AuthActivity.getIntent(this))
                var preferenceManager1 = PreferenceManager(this)
                preferenceManager1.setBooleanPreference("login",false)
                startActivity(AuthActivity.getIntent(this))
                finish()
            }
            builder.setNegativeButton("No"){dialogInterface, which ->
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

            true
        }
        fabDrawer.setOnClickListener {
           binding.drawerLayout.openDrawer(Gravity.START)
        }

        btnChoose.setOnClickListener {
            placeRepository.insertPlace(PlaceEntity(0,getAddress(latitude,longitude),"$latitude,$longitude"))
            startActivity(WeatherInfoActivity.getIntent(this,latitude,longitude))
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        latitude = 23.58675
        longitude = 72.54640
        val mahesana = LatLng(latitude, longitude)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(mahesana))

      

        mMap.setOnMarkerDragListener(object : OnMarkerDragListener {
            override fun onMarkerDragStart(arg0: Marker) {
                // TODO Auto-generated method stub
                Log.d(
                    "System out",
                    "onMarkerDragStart..." + arg0.position.latitude + "..." + arg0.position.longitude
                )
            }

            override fun onMarkerDragEnd(arg0: Marker) {
                // TODO Auto-generated method stub
                Log.d(
                    "System out",
                    "onMarkerDragEnd..." + arg0.position.latitude + "..." + arg0.position.longitude
                )
                latitude= arg0.position.latitude
                longitude= arg0.position.longitude
                tvLocation.setText(getAddress(arg0.position.latitude,arg0.position.longitude))
                arg0.title = getAddress(arg0.position.latitude,arg0.position.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.position))
            }

            override fun onMarkerDrag(arg0: Marker) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...")
            }
        })
    }

    override fun onLocationFound(location: Location?) {
        if (location != null) {
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(location.latitude, location.longitude))
                    .draggable(true)
            )
          latitude=location.latitude
          longitude=location.longitude
            tvLocation.setText(getAddress(latitude,longitude))
        }
    }
    private fun getLocation() {

        if (checkPermission()) {
            locationHelper = LocationHelper(this, this, true, false, true)

        } else {
            requestPermission()
        }
    }

    /**
     * check permission for location
     */
    private fun checkPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }
    /**
     * request permission
     */
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            22
        )
    }

    override fun onDeclineProcessForLocation() {
        showMessage(this, getString(R.string.without_location_you_cant_get_request))

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationHelper != null) {
            locationHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        when (requestCode) {
            22 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("")
                builder.setMessage(R.string.without_location_you_cant_get_request)
                builder.setPositiveButton("Ok"){dialogInterface, which ->
                    requestPermission()
                }
              // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        locationHelper?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }

}