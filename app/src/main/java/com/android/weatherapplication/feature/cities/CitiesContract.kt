package com.android.weatherapplication.feature.cities

import com.android.weatherapplication.common.BaseState
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity

/**
 * Cities contract.
 */
interface CitiesContract {

    interface ViewModel

    /**
     * @property citiesList the city list.
     */
    data class State(
        val citiesList: List<CitiesForSearchEntity> = arrayListOf()
    ) : BaseState
}
