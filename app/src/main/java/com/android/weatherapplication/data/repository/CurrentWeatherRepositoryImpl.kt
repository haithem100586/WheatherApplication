package com.android.weatherapplication.data.repository

import androidx.lifecycle.distinctUntilChanged
import com.android.weatherapplication.data.datasource.CurrentWeatherDao
import com.android.weatherapplication.domain.model.entity.CurrentWeatherEntity
import com.android.weatherapplication.domain.repository.CurrentWeatherRepository

class CurrentWeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao
) : CurrentWeatherRepository {

    override fun getCurrentWeather() = currentWeatherDao.getCurrentWeather().distinctUntilChanged()

    override suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity) =
        currentWeatherDao.insertCurrentWeather(currentWeather)

    override suspend fun deleteAndInsert(currentWeather: CurrentWeatherEntity) =
        currentWeatherDao.deleteAndInsert(currentWeather)

    override suspend fun deleteCurrentWeather() = currentWeatherDao.deleteCurrentWeather()

    override suspend fun getCount() = currentWeatherDao.getCount()
}
