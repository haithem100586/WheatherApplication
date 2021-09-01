package com.android.weatherapplication.domain.usecases

/**
 * Gets list of cities.
 */
class ListCitiesUseCase {

    fun execute(distance: Int?): String {
        return "La distance est" + distance.toString()
    }
}