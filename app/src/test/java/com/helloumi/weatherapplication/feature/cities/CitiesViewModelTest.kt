package com.helloumi.weatherapplication.feature.cities

import androidx.lifecycle.MutableLiveData
import com.helloumi.weatherapplication.common.BaseStateViewModelTest
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.usecases.ListCitiesUseCase
import com.helloumi.weatherapplication.domain.usecases.RemoveCityUseCase
import com.helloumi.weatherapplication.feature.cities.CitiesContract.State
import com.helloumi.weatherapplication.feature.main.NetworkAvailabilityMonitor
import com.snakydesign.livedataextensions.livedata.SingleLiveData
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.inject

class CitiesViewModelTest : BaseStateViewModelTest<State>() {

    override val testModule = module {
        factory { listCitiesUseCase }
        factory { removeCityUseCase }
        factory { networkAvailabilityMonitor }
        factory { CitiesViewModel(get(), get(), get()) }
    }

    @MockK
    lateinit var listCitiesUseCase: ListCitiesUseCase

    @MockK
    lateinit var removeCityUseCase: RemoveCityUseCase

    @MockK
    lateinit var networkAvailabilityMonitor: NetworkAvailabilityMonitor

    private val viewModel: CitiesViewModel by inject()

    ///////////////////////////////////////////////////////////////////////////
    // Init
    ///////////////////////////////////////////////////////////////////////////

    @Before
    fun initialState() {
        // all those tests use the default initial state, so do it here
        setupState(viewModel, State())
    }

    @Test
    fun `when getting cities, the value gets applied to state`() {
        // GIVEN
        val cityForSearchEntity = CitiesForSearchEntity(
            "ChIJ4aQWg0SVVRIR75sBqjozhDo",
            "Tunisia"
        )
        val listCities: List<CitiesForSearchEntity> = listOf(cityForSearchEntity)

        // WHEN
        every { listCitiesUseCase.execute() } returns MutableLiveData(listCities)
        viewModel.getCities()

        // THEN
        assertEquals(
            listCities,
            viewModel.currentState.citiesList
        )
    }

    @Test
    fun `when deleting city, the value gets applied to state`() {
        // GIVEN
        val cityForSearchEntity = CitiesForSearchEntity(
            "ChIJ4aQWg0SVVRIR75sBqjozhDo",
            "Tunisia"
        )

        // WHEN
        every { removeCityUseCase.execute(any()) } returns SingleLiveData(MutableLiveData(1))
        viewModel.deleteCity(3, cityForSearchEntity)

        // THEN
        assertEquals(3, viewModel.currentState.positionOfItemToDelete)
    }
}
