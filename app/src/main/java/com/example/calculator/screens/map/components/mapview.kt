package com.example.calculator.screens.map.components

import androidx.compose.runtime.Composable
import com.example.calculator.screens.map.viewmodels.LocationViewModel
import com.example.calculator.services.GetNearPlaces
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.json.JSONObject

@Composable
fun MapView(locationViewModel: LocationViewModel) {
    var listJson = JSONObject()
    val getNearPlaces = GetNearPlaces()

    var cameraPositionState = rememberCameraPositionState {
        locationViewModel.markerPosition.value?.let {
            position = CameraPosition.fromLatLngZoom(it, 6f)
        }
    }

    locationViewModel.markerPosition.value?.let { it ->
        listJson = getNearPlaces.makeAPICall(it.latitude.toString(), it.longitude.toString())
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(it, 10f)
        }
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
    ) {
        locationViewModel.markerPosition.value?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Sua localização",
                snippet = "Você está aqui",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
        }
        if (listJson.toString() != "{}") {
            val item = listJson.getJSONArray("results")
            for (i in 0 until item.length()) {
                val itemUntil = JSONObject(item[i].toString())
                val itemGeo = JSONObject(itemUntil["geometry"].toString())
                val itemLocation = JSONObject(itemGeo["location"].toString())

                Marker(
                    state = MarkerState(position = LatLng(itemLocation["lat"].toString().toDouble(),itemLocation["lng"].toString().toDouble())),
                    title = itemUntil["name"].toString(),
                    snippet = itemUntil["vicinity"].toString(),
                    alpha = 0.6f
                )
            }
        }
    }
}