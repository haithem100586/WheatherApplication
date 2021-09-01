package com.android.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.android.weatherapplication.common.WeatherDatabase
import com.android.weatherapplication.common.ext.koin.foreground
import com.android.weatherapplication.data.repository.CitiesForSearchRepositoryImpl
import com.android.weatherapplication.data.repository.CurrentWeatherRepositoryImpl
import com.android.weatherapplication.data.repository.ForecastRepositoryImpl
import com.android.weatherapplication.domain.repository.CitiesForSearchRepository
import com.android.weatherapplication.domain.repository.CurrentWeatherRepository
import com.android.weatherapplication.domain.repository.ForecastRepository
import com.android.weatherapplication.domain.usecases.AddCityUseCase
import com.android.weatherapplication.domain.usecases.ListCitiesUseCase
import com.android.weatherapplication.feature.addcity.AddCityViewModel
import com.android.weatherapplication.feature.cities.CitiesViewModel
import com.android.weatherapplication.feature.main.NetworkAvailabilityMonitor
import org.koin.android.ext.koin.androidContext
import com.android.weatherapplication.common.di.foreground as fg
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val applicationModule = module {

    // presentation
    viewModel { CitiesViewModel(fg.get()) }
    viewModel { AddCityViewModel(fg.get()) }

    ///////////////////////////////////////////////////////////////////////////
    // Domain
    ///////////////////////////////////////////////////////////////////////////
    foreground {
        scoped { createDatabase(androidContext()) }
        scoped { get<WeatherDatabase>().forecastDao() }
        scoped { get<WeatherDatabase>().currentWeatherDao() }
        scoped { get<WeatherDatabase>().citiesForSearchDao() }

        scoped { ListCitiesUseCase() }
        scoped { AddCityUseCase(get()) }

        scoped { ForecastRepositoryImpl(get()) } bind ForecastRepository::class
        scoped { CurrentWeatherRepositoryImpl(get()) } bind CurrentWeatherRepository::class
        scoped { CitiesForSearchRepositoryImpl(get()) } bind CitiesForSearchRepository::class

        // misc
        scoped { NetworkAvailabilityMonitor(get()) }
    }
}

///////////////////////////////////////////////////////////////////////////
// Internal
///////////////////////////////////////////////////////////////////////////

/**
 * Creates application database.
 *
 * @param context context.
 */
private fun createDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        WeatherDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()
