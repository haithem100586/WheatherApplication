package com.helloumi.weatherapplication.di

import com.helloumi.weatherapplication.common.WeatherDatabase
import com.helloumi.weatherapplication.common.ext.koin.foreground
import com.helloumi.weatherapplication.data.repository.CitiesForSearchRepositoryImpl
import com.helloumi.weatherapplication.data.repository.ForecastRepositoryImpl
import com.helloumi.weatherapplication.data.repository.CurrentWeatherRepositoryImpl
import com.helloumi.weatherapplication.data.repository.ApcRepositoryImpl
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository
import com.helloumi.weatherapplication.domain.repository.ForecastRepository
import com.helloumi.weatherapplication.domain.repository.CurrentWeatherRepository
import com.helloumi.weatherapplication.domain.repository.ApcRepository
import com.helloumi.weatherapplication.domain.usecases.*
import com.helloumi.weatherapplication.feature.addcity.AddCityViewModel
import com.helloumi.weatherapplication.feature.cities.CitiesViewModel
import com.helloumi.weatherapplication.feature.weather.WeatherViewModel
import com.helloumi.weatherapplication.common.di.foreground as fg
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // presentation
    viewModel { CitiesViewModel(fg.get(), fg.get(), fg.get()) }
    viewModel { AddCityViewModel(fg.get()) }
    viewModel { WeatherViewModel(androidContext(), fg.get(), fg.get()) }

    ///////////////////////////////////////////////////////////////////////////
    // Domain
    ///////////////////////////////////////////////////////////////////////////
    foreground {
        scoped { get<WeatherDatabase>().forecastDao() }
        scoped { get<WeatherDatabase>().currentWeatherDao() }
        scoped { get<WeatherDatabase>().citiesForSearchDao() }

        scoped { ListCitiesUseCase(get()) }
        scoped { AddCityUseCase(get()) }
        scoped { RemoveCityUseCase(get()) }
        scoped { GetCurrentWeatherUseCase(get()) }
        scoped { GetForecastUseCase(get()) }

        scoped { ForecastRepositoryImpl(get()) } bind ForecastRepository::class
        scoped { CurrentWeatherRepositoryImpl(get()) } bind CurrentWeatherRepository::class
        scoped { CitiesForSearchRepositoryImpl(get()) } bind CitiesForSearchRepository::class
        scoped { ApcRepositoryImpl(get()) } bind ApcRepository::class
    }
}
