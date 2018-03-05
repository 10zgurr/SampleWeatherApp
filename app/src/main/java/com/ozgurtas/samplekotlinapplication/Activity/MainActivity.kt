package com.ozgurtas.samplekotlinapplication.Activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.ArrayAdapter
import com.ozgurtas.samplekotlinapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import com.ozgurtas.samplekotlinapplication.Utils.NavigationHelper

class MainActivity : BaseActivity() {

    private val REQUEST_LOCATION = 1

    override fun onCreateFinished(savedInstanceState: Bundle?) {
        //Fill the Spinner from 1 to 10
        val arrayList = (1..10).map { it.toString() + "" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList)
        spinnerQuantity.adapter = adapter

        btnCurrent.setOnClickListener {
            val location = getLocation()
            if (location != null)
                NavigationHelper().getInstance().startCurrentWeatherActivity(this@MainActivity, location.latitude, location.longitude)
        }

        btnForecast.setOnClickListener {
            val location = getLocation()
            val quantity = spinnerQuantity.selectedItem.toString()
            if (location != null)
                NavigationHelper().getInstance().startForecastWeatherActivity(this@MainActivity, location.latitude, location.longitude, quantity)
        }

        btnLocation.setOnClickListener {
            NavigationHelper().getInstance().startSearchActivity(this@MainActivity)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    //Ask Location Permission to the User
    private fun isLocationPermissionGranted(): Boolean = if (Build.VERSION.SDK_INT >= 23) {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
            false
        }
    } else {
        //Permission is Automatically Granted on SDK < 23 upon Installation
        true
    }

    //Get Location Info After Permission
    private fun getLocation(): Location? = if (isLocationPermissionGranted()) {
        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null)
            location as Location
        else {
            showToast("Please open your location service")
            null
        }
    } else
        null
}
