package com.helloumi.weatherapplication.feature.addcity

import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.common.BaseStateViewModelTest
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.usecases.AddCityUseCase
import com.helloumi.weatherapplication.feature.addcity.AddCityContract.State
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.inject

class AddCityViewModelTest : BaseStateViewModelTest<State>() {

    override val testModule = module {
        factory { addCityUseCase }
        factory { AddCityViewModel(get()) }
    }

    @MockK
    lateinit var addCityUseCase: AddCityUseCase

    private val viewModel: AddCityViewModel by inject()

    ///////////////////////////////////////////////////////////////////////////
    // Init
    ///////////////////////////////////////////////////////////////////////////

    @Before
    fun initialState() {
        // all those tests use the default initial state, so do it here
        setupState(viewModel, State())
    }

    ///////////////////////////////////////////////////////////////////////////
    // Tests
    ///////////////////////////////////////////////////////////////////////////

    @Test
    fun `when adding city, the value gets applied to state`() {
        // GIVEN
        val cityForSearchEntity = CitiesForSearchEntity(
            "ChIJ4aQWg0SVVRIR75sBqjozhDo",
            "Tunisia"
        )

        // WHEN
        cityForSearchEntity.name?.let { viewModel.setCity(cityForSearchEntity.id, it) }
        viewModel.addCity()

        // THEN
        verify {
            stateObserver.onChanged(
                State(
                    cityForSearchEntity = cityForSearchEntity,
                    messageResId = R.string.add_city_success,
                    isCityAdded = true
                )
            )
        }
    }
}
