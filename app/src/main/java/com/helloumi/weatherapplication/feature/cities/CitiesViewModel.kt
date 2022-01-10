package com.helloumi.weatherapplication.feature.cities

import com.helloumi.weatherapplication.feature.cities.CitiesContract.State
import com.helloumi.weatherapplication.feature.cities.CitiesContract.ViewModel
import com.helloumi.weatherapplication.common.BaseEvent
import com.helloumi.weatherapplication.common.BaseStateViewModel
import com.helloumi.weatherapplication.common.ext.android.observeOnce
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.usecases.ListCitiesUseCase
import com.helloumi.weatherapplication.domain.usecases.RemoveCityUseCase
import com.helloumi.weatherapplication.feature.main.NetworkAvailabilityMonitor

/**
 * Cities ViewModel.
 */
class CitiesViewModel(
    listCitiesUseCase: ListCitiesUseCase,
    private val removeCityUseCase: RemoveCityUseCase,
    networkAvailabilityMonitor: NetworkAvailabilityMonitor,
    initialState: State = State()
) : BaseStateViewModel<State, BaseEvent>(initialState), ViewModel {

    init {
        listCitiesUseCase.execute().observe(this) {
            updateState { state -> state.copy(citiesList = it) }
        }
        networkAvailabilityMonitor.observe(this) { available ->
            updateState { state -> state.copy(isInternetAvailable = available) }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewModel contract implementation
    ///////////////////////////////////////////////////////////////////////////

    override fun deleteCity(city: CitiesForSearchEntity) =
        removeCityUseCase.execute(city).observeOnce(this)
}
