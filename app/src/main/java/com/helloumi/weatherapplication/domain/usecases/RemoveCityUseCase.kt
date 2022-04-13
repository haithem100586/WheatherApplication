package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository

/**
 * Removes city in database.
 */
class RemoveCityUseCase(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     *
     * @return liveData of Int.
     */
    fun execute(city: CitiesForSearchEntity) = citiesForSearchRepository.delete(city)
}
