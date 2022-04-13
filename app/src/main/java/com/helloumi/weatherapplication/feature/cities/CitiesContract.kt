package com.helloumi.weatherapplication.feature.cities

import androidx.annotation.StringRes
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.common.BaseState
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity

/**
 * Cities contract.
 */
interface CitiesContract {

    interface ViewModel {
        /**
         * Deletes a city from data.
         *
         * @param position the position of item to delete.
         * @param city the city.
         */
        fun deleteCity(position:Int, city: CitiesForSearchEntity)
    }

    /**
     * @property citiesList the city list.
     * @property isInternetAvailable when is internet available or not.
     * @property positionOfItemToDelete the position of city to delete.
     * @property messageResId resource id for message.
     */
    data class State(
        val citiesList: List<CitiesForSearchEntity> = arrayListOf(),
        val isInternetAvailable: Boolean = false,
        val positionOfItemToDelete: Int? = null,
        @StringRes val messageResId: Int = R.string.delete_city_success
    ) : BaseState
}
