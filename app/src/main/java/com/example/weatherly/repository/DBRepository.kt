package com.example.weatherly.repository

import com.example.weatherly.data.WeatherDao
import com.example.weatherly.model.Favorite
import com.example.weatherly.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun addFavorite(favorite: Favorite) = weatherDao.addFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.removeFavorite(favorite)

    suspend fun getFavByID(city: String): Favorite = weatherDao.favByID(city)

    //Units table

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()

    suspend fun addUnit(unit: Unit) = weatherDao.addUnits(unit)

    suspend fun deleteUnit(unit: Unit) = weatherDao.removeUnit(unit)


}