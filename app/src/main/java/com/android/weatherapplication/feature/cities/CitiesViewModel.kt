package com.android.weatherapplication.feature.cities

import com.android.weatherapplication.feature.cities.CitiesContract.State
import com.android.weatherapplication.feature.cities.CitiesContract.ViewModel
import com.android.weatherapplication.common.BaseEvent
import com.android.weatherapplication.common.BaseStateViewModel
import com.android.weatherapplication.domain.usecases.ListCitiesUseCase

/**
 * Cities ViewModel.
 */
class CitiesViewModel(
    listCitiesUseCase: ListCitiesUseCase,
    initialState: State = State()
) : BaseStateViewModel<State, BaseEvent>(initialState), ViewModel {

    init {
        listCitiesUseCase.execute().observe(this) {
            updateState { state -> state.copy(citiesList = it) }
        }
    }
}
