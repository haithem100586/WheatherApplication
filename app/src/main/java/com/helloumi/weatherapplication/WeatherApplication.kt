package com.helloumi.weatherapplication

import android.app.Application
import com.helloumi.weatherapplication.common.BaseApplication
import com.helloumi.weatherapplication.di.appModule
import com.helloumi.weatherapplication.di.databaseModule
import com.helloumi.weatherapplication.di.networkModule

/**
 * [Application] for WeatherApplication.
 *
 * Sets all application-wide frameworks, including:
 * - dependency injection (Koin)
 */
class WeatherApplication : BaseApplication() {

    /**
     * All loaded dependency injection modules.
     */
    override val modules = listOf(
        appModule,
        networkModule,
        databaseModule
    )
}
