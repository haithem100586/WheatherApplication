package com.helloumi.weatherapplication

import com.helloumi.weatherapplication.di.appModule
import com.helloumi.weatherapplication.di.databaseModule
import com.helloumi.weatherapplication.di.networkModule

/**
 * Weather product: contains all features specific to WeatherApplication.
 */
object Product : BaseProduct() {

    override val modules = listOf(
        appModule,
        networkModule,
        databaseModule
    )
}
