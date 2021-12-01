package com.android.weatherapplication.feature.addcity

import androidx.annotation.StringRes
import com.android.weatherapplication.R
import com.android.weatherapplication.common.BaseState
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity

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
    }

    /**
     * @property cityForSearchEntity the searched city.
     * @property messageResId resource id for message.
     * @property isCityAdded when city is added or not.
     */
    data class State(
        var cityForSearchEntity: CitiesForSearchEntity? = null,
        @StringRes val messageResId: Int = R.string.add_city_success,
        val isCityAdded: Boolean = false
    ) : BaseState
}
