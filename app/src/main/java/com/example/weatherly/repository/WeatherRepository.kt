package com.example.weatherly.repository

import android.util.Log
import com.example.weatherly.data.DataOrException
import com.example.weatherly.model.Weather
import com.example.weatherly.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(val api: WeatherAPI) {
    suspend fun getWeather(cityQuery: String):
            DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        }catch (e: Exception){
            Log.d("Exception IS:", "EXCEPTION FOUND: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }
}