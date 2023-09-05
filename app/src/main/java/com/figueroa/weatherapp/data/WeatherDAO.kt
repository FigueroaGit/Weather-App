package com.figueroa.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.figueroa.weatherapp.model.Favorite
import com.figueroa.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDAO {
    @Query(value = "SELECT * FROM favorite_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query(value = "SELECT * FROM favorite_tbl WHERE city =:city")
    suspend fun getFavoritesByID(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query(value = "DELETE FROM favorite_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query(value = "SELECT * FROM settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query(value = "DELETE FROM settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)
}
