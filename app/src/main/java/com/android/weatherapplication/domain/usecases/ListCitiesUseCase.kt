package com.android.weatherapplication.domain.usecases

import androidx.lifecycle.LiveData
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.repository.CitiesForSearchRepository

/**
 * Gets list of cities.
 */
class ListCitiesUseCase(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     */
    fun execute(): LiveData<List<CitiesForSearchEntity>> = citiesForSearchRepository.getAllCities()
}
