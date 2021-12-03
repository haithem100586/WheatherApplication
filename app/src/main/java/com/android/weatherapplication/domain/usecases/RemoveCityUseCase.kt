package com.android.weatherapplication.domain.usecases

import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.repository.CitiesForSearchRepository

/**
 * Removes city in database.
 */
class RemoveCityUseCase(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     */
    fun execute(city: CitiesForSearchEntity) = citiesForSearchRepository.delete(city)
}