package com.helloumi.weatherapplication.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.helloumi.weatherapplication.domain.model.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM CurrentWeather")
    fun getCurrentWeather(): LiveData<CurrentWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Transaction
    suspend fun deleteAndInsert(currentWeatherEntity: CurrentWeatherEntity) {
        deleteCurrentWeather()
        insertCurrentWeather(currentWeatherEntity)
    }

    @Query("DELETE FROM CurrentWeather")
    suspend fun deleteCurrentWeather()

    @Query("Select count(*) from CurrentWeather")
    suspend fun getCount(): Int
}
