package com.example.weatherly.screen.main

import androidx.lifecycle.ViewModel
import com.example.weatherly.data.DataOrException
import com.example.weatherly.model.Weather
import com.example.weatherly.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    suspend fun getWeatherData(city: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }
}