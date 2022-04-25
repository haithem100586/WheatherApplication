package com.helloumi.weatherapplication.di

import android.content.Context
import com.helloumi.weatherapplication.common.MoshiIgnoreAdapter
import com.helloumi.weatherapplication.common.ext.koin.foreground
import com.helloumi.weatherapplication.data.api.apc.ApcAPI
import com.helloumi.weatherapplication.feature.main.NetworkAvailabilityMonitor
import com.helloumi.weatherapplication.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    foreground {
        scoped {
            Moshi.Builder().add(KotlinJsonAdapterFactory()).add(MoshiIgnoreAdapter.FACTORY).build()
        }
        scoped { MoshiConverterFactory.create(get()) }

        scoped { createHttpClient(androidContext()) }
        scoped { createApcAPI(get(), get()) }
        // misc
        scoped { NetworkAvailabilityMonitor(get()) }
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
