package com.helloumi.weatherapplication.domain.repository

import androidx.lifecycle.LiveData
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.snakydesign.livedataextensions.livedata.SingleLiveData

interface CitiesForSearchRepository {

    /**
     * Gets all cities.
     *
     * @return the [CitiesForSearchEntity] [LiveData] [List].
     */
    fun getAllCities(): LiveData<List<CitiesForSearchEntity>>

    /**
     * Gets city by name.
     *
     * @param cityName the name of city.
     *
     * @return the [CitiesForSearchEntity] [LiveData] [List].
     */
    fun getCityByName(cityName: String? = ""): LiveData<List<CitiesForSearchEntity>>

    /**
     * Inserts city.
     *
     * @param city the city to insert.
     *
     * @return the resulting LiveData with the id of the saved city.
     */
    suspend fun insertCity(city: CitiesForSearchEntity): Long

    /**
     * Inserts cities.
     *
     * @param cities the cities to insert.
     */
    suspend fun insertCities(cities: List<CitiesForSearchEntity>): List<Long>

    /**
     * Deletes a city.
     *
     * @param city the city to delete.
     *
     * @return the resulting LiveData with the number of deleted flights.
     */
    fun delete(city: CitiesForSearchEntity): SingleLiveData<Int>

    /**
     * Deletes all cities.
     */
    suspend fun deleteCities()

    /**
     * Gets cities count.
     *
     * @return the count of cities.
     */
    suspend fun getCount(): Int
}
