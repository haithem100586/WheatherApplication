package com.helloumi.weatherapplication.feature.addcity

import androidx.lifecycle.viewModelScope
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.feature.addcity.AddCityContract.State
import com.helloumi.weatherapplication.feature.addcity.AddCityContract.ViewModel
import com.helloumi.weatherapplication.common.BaseEvent
import com.helloumi.weatherapplication.common.BaseStateViewModel
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.usecases.AddCityUseCase
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
        updateState { it.copy(messageResId = null) }
        if (currentState.cityForSearchEntity == null) {
            updateState { it.copy(messageResId = R.string.add_city_country_required) }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                currentState.cityForSearchEntity?.let { addCityUseCase.execute(it) }
            }
            updateState { it.copy(messageResId = R.string.add_city_success, isCityAdded = true) }
        }
    }

    override fun resetCity() {
        updateState { it.copy(cityForSearchEntity = null) }
    }
}
