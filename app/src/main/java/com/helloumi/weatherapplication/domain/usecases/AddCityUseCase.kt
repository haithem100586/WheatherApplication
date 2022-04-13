package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository

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
