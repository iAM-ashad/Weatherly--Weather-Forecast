package com.example.weatherly.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherly.model.Favorite
import com.example.weatherly.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDB: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}