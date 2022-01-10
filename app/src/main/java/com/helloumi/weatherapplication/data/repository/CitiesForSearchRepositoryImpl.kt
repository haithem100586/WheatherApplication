package com.helloumi.weatherapplication.data.repository

import androidx.lifecycle.liveData
import com.helloumi.weatherapplication.data.datasource.CitiesForSearchDao
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository
import com.snakydesign.livedataextensions.toSingleLiveData
import kotlinx.coroutines.Dispatchers

class CitiesForSearchRepositoryImpl(
    private val citiesForSearchDao: CitiesForSearchDao
) : CitiesForSearchRepository {

    override fun getAllCities() = citiesForSearchDao.getCities()

    override fun getCityByName(cityName: String?) = citiesForSearchDao.getCityByName(cityName)

    override suspend fun insertCity(city: CitiesForSearchEntity) = citiesForSearchDao.insert(city)

    override suspend fun insertCities(cities: List<CitiesForSearchEntity>) =
        citiesForSearchDao.insert(cities)

    override fun delete(city: CitiesForSearchEntity) =
        liveData(Dispatchers.IO) { emit(citiesForSearchDao.delete(city)) }.toSingleLiveData()

    override suspend fun deleteCities() = citiesForSearchDao.deleteCities()

    override suspend fun getCount() = citiesForSearchDao.getCount()
}
