package com.android.weatherapplication.data.repository

import androidx.lifecycle.liveData
import com.android.weatherapplication.data.datasource.CitiesForSearchDao
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.repository.CitiesForSearchRepository
import com.snakydesign.livedataextensions.toSingleLiveData
import kotlinx.coroutines.Dispatchers

class CitiesForSearchRepositoryImpl(
    private val citiesForSearchDao: CitiesForSearchDao
) : CitiesForSearchRepository {

    override fun getAllCities() = citiesForSearchDao.getCities()

    override fun getCityByName(cityName: String?) = citiesForSearchDao.getCityByName(cityName)

    override suspend fun insertCity(city: CitiesForSearchEntity) =
        citiesForSearchDao.insertCity(city)

    override suspend fun insertCities(cities: List<CitiesForSearchEntity>) =
        citiesForSearchDao.insertCities(cities)

    override fun delete(city: CitiesForSearchEntity) =
        liveData(Dispatchers.IO) { emit(citiesForSearchDao.delete(city)) }.toSingleLiveData()

    override suspend fun deleteCities() = citiesForSearchDao.deleteCities()

    override suspend fun getCount() = citiesForSearchDao.getCount()
}
