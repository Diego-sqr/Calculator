package com.example.calculator.services

import android.os.StrictMode
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class GetNearPlaces() {

    fun makeAPICall(latitude: String, longitude: String): JSONObject {
        val client = OkHttpClient()
        StrictMode
            .setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())

        val request = Request.Builder()
            .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=delegacia+mulher&location=${latitude},${longitude}&radius=10000&key=AIzaSyAA7-2KdqovpIxCId_QJa7kwyjIwRiXjxE")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                return JSONObject("")
            } else {
                val responseBody = response.body!!.string()
                return JSONObject(responseBody)
            }
        }
    }
}