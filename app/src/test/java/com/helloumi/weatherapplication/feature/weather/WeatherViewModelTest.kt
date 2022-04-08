package com.helloumi.weatherapplication.feature.weather

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.helloumi.weatherapplication.common.BaseStateViewModelTest
import com.helloumi.weatherapplication.domain.model.*
import com.helloumi.weatherapplication.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.weatherapplication.domain.usecases.GetForecastUseCase
import com.helloumi.weatherapplication.feature.weather.WeatherContract.State
import com.snakydesign.livedataextensions.livedata.SingleLiveData
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.inject

class WeatherViewModelTest : BaseStateViewModelTest<State>() {

    override val testModule = module {
        factory { context }
        factory { getCurrentWeatherUseCase }
        factory { getForecastUseCase }
        factory { WeatherViewModel(get(), get(), get()) }
    }

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @MockK
    lateinit var getForecastUseCase: GetForecastUseCase

    private val viewModel: WeatherViewModel by inject()

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
    fun `when getting current weather, the value gets applied to state`() {

        // GIVEN
        val cityName = "Tunisia"

        val listWeatherItem: List<WeatherItem?> = listOf(
            WeatherItem(
                icon = "01n",
                description = "clear sky",
                main = "Clear",
                id = 800
            )
        )
        val currentWeatherResponse = CurrentWeatherResponse(
            visibility = 10000,
            timezone = 3600,
            main = Main(
                temp = 20.59,
                tempMin = 20.59,
                grndLevel = 1007.0,
                tempKf = null,
                humidity = 30,
                pressure = 1010.0,
                seaLevel = 1010.0,
                tempMax = 20.59
            ),
            clouds = Clouds(all = 1),
            sys = Sys(pod = null),
            dt = 1648839805,
            coord = Coord(lon = 9.0, lat = 34.0),
            weather = listWeatherItem,
            name = "Tunisia",
            cod = 200,
            id = 2464461,
            base = "stations",
            wind = Wind(deg = 263.0, speed = 5.17)
        )

        // WHEN
        every { getCurrentWeatherUseCase.execute(any()) } returns SingleLiveData(
            MutableLiveData(
                CurrentWeatherResult.Success(currentWeatherResponse)
            )
        )
        viewModel.getCurrentWeather(cityName)

        // THEN
        assertEquals(
            State(
                isWeatherTaken = true,
                isForecastTaken = null,
                currentWeatherResponse = currentWeatherResponse,
                forecastResponse = null,
                weatherIconResId = 0,
                errorText = null
            ),
            viewModel.currentState
        )
    }

    @Test
    fun `when getting current forecast, the value gets applied to state`() {
        // GIVEN
        val cityName = "Tunisia"
        val listWeatherItem: List<WeatherItem?> = listOf(
            WeatherItem(
                icon = "04n",
                description = "overcast clouds",
                main = "Clouds",
                id = 804
            )
        )
        val listItem = listOf(
            ListItem(
                dt = 1648069200,
                rain = null,
                dtTxt = "2022-03-23 21:00:00",
                snow = null,
                weather = listWeatherItem,
                main = Main(
                    temp = 15.89,
                    tempMin = 13.97,
                    grndLevel = 1024.0,
                    tempKf = 1.92,
                    humidity = 53,
                    pressure = 1026.0,
                    seaLevel = 1026.0,
                    tempMax = 15.89
                ),
                clouds = Clouds(all = 100),
                sys = Sys(pod = "n"),
                wind = Wind(deg = 82.0, speed = 8.33)
            )
        )
        val forecastResponse =
            ForecastResponse(city = null, cnt = null, cod = null, message = null, listItem)

        // WHEN
        every { getForecastUseCase.execute(any()) } returns SingleLiveData(
            MutableLiveData(
                ForecastResult.Success(forecastResponse)
            )
        )
        viewModel.getForecast(cityName)

        // THEN
        assertEquals(
            State(
                isWeatherTaken = null,
                isForecastTaken = true,
                currentWeatherResponse = null,
                forecastResponse = forecastResponse,
                weatherIconResId = null,
                errorText = null
            ),
            viewModel.currentState
        )
    }
}
