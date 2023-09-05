package com.figueroa.weatherapp.repository

import com.figueroa.weatherapp.data.WeatherDAO
import com.figueroa.weatherapp.model.Favorite
import com.figueroa.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDatabaseRepository @Inject constructor(private val weatherDAO: WeatherDAO) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDAO.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDAO.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDAO.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDAO.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDAO.deleteFavorite(favorite)
    suspend fun getFavoriteByID(city: String): Favorite = weatherDAO.getFavoritesByID(city)

    fun getUnits(): Flow<List<Unit>> = weatherDAO.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDAO.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDAO.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDAO.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDAO.deleteUnit(unit)
}
