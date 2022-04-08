package com.helloumi.weatherapplication.domain.usecases

import androidx.lifecycle.LiveData
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository

/**
 * Gets list of cities.
 */
class ListCitiesUseCase(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     *
     * @return list of CitiesForSearchEntity LiveData.
     */
    fun execute(): LiveData<List<CitiesForSearchEntity>> = citiesForSearchRepository.getAllCities()
}
