package com.android.weatherapplication.domain.usecases

import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.repository.CitiesForSearchRepository

/**
 * Adds city in database.
 */
class AddCityUseCase(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     */
    suspend fun execute(citiesForSearchEntity: CitiesForSearchEntity) =
        citiesForSearchRepository.insertCity(citiesForSearchEntity)
}
