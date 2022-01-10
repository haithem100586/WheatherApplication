package com.helloumi.weatherapplication.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherItem(

    @Json(name = "icon")
    val icon: String?,

    @Json(name = "description")
    val description: String?,

    @Json(name = "main")
    val main: String?,

    @Json(name = "id")
    val id: Int?
) : Parcelable
