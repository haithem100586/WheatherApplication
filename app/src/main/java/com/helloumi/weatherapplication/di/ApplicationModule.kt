package com.helloumi.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.helloumi.weatherapplication.common.MoshiIgnoreAdapter
import com.helloumi.weatherapplication.common.WeatherDatabase
import com.helloumi.weatherapplication.common.ext.koin.foreground
import com.helloumi.weatherapplication.data.api.apc.ApcAPI
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
import com.helloumi.weatherapplication.feature.main.NetworkAvailabilityMonitor
import com.helloumi.weatherapplication.feature.weather.WeatherViewModel
import com.helloumi.weatherapplication.utils.Constants
import com.helloumi.weatherapplication.common.di.foreground as fg
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val applicationModule = module {

    // presentation
    viewModel { CitiesViewModel(fg.get(), fg.get(), fg.get()) }
    viewModel { AddCityViewModel(fg.get()) }
    viewModel { WeatherViewModel(androidContext(), fg.get(), fg.get()) }

    ///////////////////////////////////////////////////////////////////////////
    // Data
    ///////////////////////////////////////////////////////////////////////////
    foreground {
        scoped { createHttpClient(androidContext()) }
        scoped {
            Moshi.Builder().add(KotlinJsonAdapterFactory()).add(MoshiIgnoreAdapter.FACTORY).build()
        }
        scoped { MoshiConverterFactory.create(get()) }
        scoped { createDatabase(androidContext()) }
        scoped { createApcAPI(get(), get()) }
        // misc
        scoped { NetworkAvailabilityMonitor(get()) }
    }

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

///////////////////////////////////////////////////////////////////////////
// Internal
///////////////////////////////////////////////////////////////////////////

private const val TIMEOUT_SECONDS = 15
private const val CACHE_SIZE = 10 * 1024 * 1024

private fun createHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, CACHE_SIZE.toLong()))
        .readTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
        .build()
}

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

/**
 * Creates an API to access APC web-services.
 *
 * @param httpClient the HTTP client to use.
 * @param moshiConverterFactory the JSON to object converter factory.
 *
 * @return the resulting [ApcAPI].
 */
private fun createApcAPI(
    httpClient: OkHttpClient,
    moshiConverterFactory: MoshiConverterFactory
): ApcAPI {
    val apcHttpClient = httpClient.newBuilder().build()
    val retrofit = Retrofit.Builder()
        .client(apcHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .baseUrl(Constants.NetworkService.BASE_URL)
        .build()
    return retrofit.create(ApcAPI::class.java)
}
