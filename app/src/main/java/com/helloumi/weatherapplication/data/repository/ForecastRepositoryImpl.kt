package com.helloumi.weatherapplication.data.repository

import androidx.lifecycle.distinctUntilChanged
import com.helloumi.weatherapplication.data.datasource.ForecastDao
import com.helloumi.weatherapplication.domain.model.entity.ForecastEntity
import com.helloumi.weatherapplication.domain.repository.ForecastRepository

class ForecastRepositoryImpl(
    private val forecastDao: ForecastDao
) : ForecastRepository {

    override fun getAllForecast() = forecastDao.getForecast().distinctUntilChanged()

    override fun getForecastByCoord(lat: Double, lon: Double) =
        forecastDao.getForecastByCoord(lat, lon).distinctUntilChanged()

    override suspend fun insertForecast(forecast: ForecastEntity) =
        forecastDao.insertForecast(forecast)

    override suspend fun deleteAndInsert(forecast: ForecastEntity) =
        forecastDao.deleteAndInsert(forecast)

    override suspend fun updateForecast(forecast: ForecastEntity) =
        forecastDao.updateForecast(forecast)

    override suspend fun deleteForecast(forecast: ForecastEntity) =
        forecastDao.deleteForecast(forecast)

    override suspend fun deleteAllForecast() = forecastDao.deleteAll()

    override suspend fun getCount() = forecastDao.getCount()
}
