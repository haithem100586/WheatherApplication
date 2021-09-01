package com.android.weatherapplication.data.repository

import com.android.weatherapplication.data.datasource.CitiesForSearchDao
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.repository.CitiesForSearchRepository

class CitiesForSearchRepositoryImpl(
    private val citiesForSearchDao: CitiesForSearchDao
) : CitiesForSearchRepository {

    override fun getAllCities() = citiesForSearchDao.getCities()

    override fun getCityByName(cityName: String?) = citiesForSearchDao.getCityByName(cityName)

    override suspend fun insertCity(city: CitiesForSearchEntity) =
        citiesForSearchDao.insertCity(city)

    override suspend fun insertCities(cities: List<CitiesForSearchEntity>) =
        citiesForSearchDao.insertCities(cities)

    override suspend fun deleteCities() = citiesForSearchDao.deleteCities()

    override suspend fun getCount() = citiesForSearchDao.getCount()
}
