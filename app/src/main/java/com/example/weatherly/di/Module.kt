package com.example.weatherly.di

import android.content.Context
import androidx.room.Room
import com.example.weatherly.data.WeatherDB
import com.example.weatherly.data.WeatherDao
import com.example.weatherly.network.WeatherAPI
import com.example.weatherly.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDB: WeatherDB): WeatherDao = weatherDB.weatherDao()

    @Provides
    @Singleton
    fun provideWeatherDB(@ApplicationContext context: Context): WeatherDB =
        Room.databaseBuilder(
            context,
            WeatherDB::class.java,
            "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOpenWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}