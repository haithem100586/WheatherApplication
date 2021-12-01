package com.android.weatherapplication.feature.cities

import androidx.annotation.StringRes
import com.android.weatherapplication.R
import com.android.weatherapplication.common.BaseState
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity

/**
 * Cities contract.
 */
interface CitiesContract {

    interface ViewModel {
        /**
         * Deletes a city from data.
         *
         * @param city the city.
         */
        fun deleteCity(city: CitiesForSearchEntity)
    }

    /**
     * @property citiesList the city list.
     * @property isInternetAvailable when is internet available or not.
     * @property messageResId resource id for message.
     */
    data class State(
        val citiesList: List<CitiesForSearchEntity> = arrayListOf(),
        val isInternetAvailable: Boolean = false,
        @StringRes val messageResId: Int = R.string.delete_city_success
    ) : BaseState
}
