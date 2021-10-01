package com.android.weatherapplication.feature.cities

import com.android.weatherapplication.feature.cities.CitiesContract.State
import com.android.weatherapplication.feature.cities.CitiesContract.ViewModel
import com.android.weatherapplication.common.BaseEvent
import com.android.weatherapplication.common.BaseStateViewModel
import com.android.weatherapplication.common.ext.android.observeOnce
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.usecases.ListCitiesUseCase
import com.android.weatherapplication.domain.usecases.RemoveCityUseCase

/**
 * Cities ViewModel.
 */
class CitiesViewModel(
    listCitiesUseCase: ListCitiesUseCase,
    private val removeCityUseCase: RemoveCityUseCase,
    initialState: State = State()
) : BaseStateViewModel<State, BaseEvent>(initialState), ViewModel {

    init {
        listCitiesUseCase.execute().observe(this) {
            updateState { state -> state.copy(citiesList = it) }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewModel contract implementation
    ///////////////////////////////////////////////////////////////////////////

    override fun deleteCity(city: CitiesForSearchEntity) =
        removeCityUseCase.execute(city).observeOnce(this)
}
