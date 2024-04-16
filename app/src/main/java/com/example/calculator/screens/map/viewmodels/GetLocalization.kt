package com.example.calculator.screens.map.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)
    var markerPosition = mutableStateOf<LatLng?>(null)
        private set
    var location = mutableStateOf<Location?>(null)
        private set

    var address = mutableStateOf<Address?>(null)

    fun getAddressFromLocation(location: Location) {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses!!.isNotEmpty()) {
                address.value = addresses[0]
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) { // Adicione esta verificação
                this.location.value = location
                markerPosition.value = LatLng(location.latitude, location.longitude)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        getLastLocation()

        val locationRequest = LocationRequest.Builder(10000)
            .setMinUpdateIntervalMillis(5000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    this@LocationViewModel.location.value = location
                    markerPosition.value = LatLng(location.latitude, location.longitude)
                    getAddressFromLocation(location)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}
