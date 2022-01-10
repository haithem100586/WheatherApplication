package com.helloumi.weatherapplication

import com.helloumi.weatherapplication.di.applicationModule

/**
 * Weather product: contains all features specific to WeatherApplication.
 */
@Suppress("unused")
object Product : BaseProduct() {

    override val modules = listOf(
        applicationModule
    )
}
