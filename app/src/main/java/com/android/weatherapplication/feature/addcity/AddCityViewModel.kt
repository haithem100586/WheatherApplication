package com.android.weatherapplication.feature.addcity

import androidx.lifecycle.viewModelScope
import com.android.weatherapplication.feature.addcity.AddCityContract.State
import com.android.weatherapplication.feature.addcity.AddCityContract.ViewModel
import com.android.weatherapplication.common.BaseEvent
import com.android.weatherapplication.common.BaseStateViewModel
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.usecases.AddCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Add city ViewModel.
 */
class AddCityViewModel(
    private val addCityUseCase: AddCityUseCase,
    initialState: State = State()
) : BaseStateViewModel<State, BaseEvent>(initialState), ViewModel {

    ///////////////////////////////////////////////////////////////////////////
    // ViewModel contract implementation
    ///////////////////////////////////////////////////////////////////////////

    override fun setCity(cityId: String, cityName: String) {
        val city = CitiesForSearchEntity(cityName = cityName, cityId = cityId)
        updateState { it.copy(cityForSearchEntity = city) }
    }

    override fun addCity() {
        viewModelScope.launch(Dispatchers.IO) {
            currentState.cityForSearchEntity?.let { addCityUseCase.execute(it) }
        }
        updateState { it.copy(isCityAdded = true) }
    }
}
