package com.helloumi.weatherapplication.data.api.apc

import com.helloumi.weatherapplication.domain.model.CurrentWeatherResponse
import com.helloumi.weatherapplication.domain.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApcAPI {

    @GET("weather?")
    suspend fun getCurrentWeatherByCityName(
        @Query("q")
        cityName: String,
        @Query("units")
        units: String,
        @Query("appid")
        appId: String
    ): Response<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getForecastByCityName(
        @Query("q")
        cityName: String,
        @Query("units")
        units: String,
        @Query("appid")
        appId: String
    ): Response<ForecastResponse>
}
