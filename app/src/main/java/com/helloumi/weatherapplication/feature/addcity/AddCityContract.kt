package com.helloumi.weatherapplication.feature.addcity

import androidx.annotation.StringRes
import com.helloumi.weatherapplication.common.BaseState
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity

/**
 * Add city contract.
 */
interface AddCityContract {

    interface ViewModel {

        /**
         * Sets searched city.
         *
         * @param cityId the id of city.
         * @param cityName the name of city.
         */
        fun setCity(cityId: String, cityName: String)

        /**
         * Adds city in data base.
         */
        fun addCity()

        /**
         * Resets cityForSearchEntity.
         */
        fun resetCity()
    }

    /**
     * @property cityForSearchEntity the searched city.
     * @property messageResId resource id for message.
     * @property isCityAdded when city is added or not.
     */
    data class State(
        var cityForSearchEntity: CitiesForSearchEntity? = null,
        @StringRes val messageResId: Int? = null,
        val isCityAdded: Boolean = false
    ) : BaseState
}
