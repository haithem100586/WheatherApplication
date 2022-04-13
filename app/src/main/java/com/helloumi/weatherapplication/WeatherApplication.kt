package com.helloumi.weatherapplication

import android.app.Application
import com.helloumi.weatherapplication.common.BaseApplication
import com.helloumi.weatherapplication.di.applicationModule

/**
 * [Application] for WeatherApplication.
 *
 * Sets all application-wide frameworks, including:
 * - dependency injection (Koin)
 */
@Suppress("unused")
class WeatherApplication : BaseApplication() {

    /**
     * All loaded dependency injection modules.
     */
    override val modules = listOf(
        applicationModule
    )
}
