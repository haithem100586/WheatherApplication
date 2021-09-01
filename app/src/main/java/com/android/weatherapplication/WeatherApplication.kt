package com.android.weatherapplication

import android.app.Application
import com.android.weatherapplication.common.BaseApplication
import com.android.weatherapplication.di.applicationModule

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
