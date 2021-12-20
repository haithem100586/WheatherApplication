package com.android.weatherapplication.domain.model

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.*


@Parcelize
@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(

    @Json(name = "visibility")
    val visibility: Int? = null,

    @Json(name = "timezone")
    val timezone: Int? = null,

    @Json(name = "main")
    val main: Main? = null,

    @Json(name = "clouds")
    val clouds: Clouds? = null,

    @Json(name = "sys")
    val sys: Sys? = null,

    @Json(name = "dt")
    val dt: Int? = null,

    @Json(name = "coord")
    val coord: Coord? = null,

    @Json(name = "weather")
    val weather: List<WeatherItem?>? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "cod")
    val cod: Int? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "base")
    val base: String? = null,

    @Json(name = "wind")
    val wind: Wind? = null
) : Parcelable

{
    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: Long): DayOfWeek? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s * 1000)
            val formattedDate = sdf.format(netDate)

            LocalDate.of(
                formattedDate.substringAfterLast("/").toInt(),
                formattedDate.substringAfter("/").take(2).toInt(),
                formattedDate.substringBefore("/").toInt()
            )
                .dayOfWeek
        } catch (e: Exception) {
            e.printStackTrace()
            DayOfWeek.MONDAY
        }
    }

    fun getColor(): Int {
        return when (dt?.let { getDateTime(it.toLong()) }) {
            DayOfWeek.MONDAY -> Color.parseColor("#28E0AE")
            DayOfWeek.TUESDAY -> Color.parseColor("#FF0090")
            DayOfWeek.WEDNESDAY -> Color.parseColor("#FFAE00")
            DayOfWeek.THURSDAY -> Color.parseColor("#0090FF")
            DayOfWeek.FRIDAY -> Color.parseColor("#DC0000")
            DayOfWeek.SATURDAY -> Color.parseColor("#0051FF")
            DayOfWeek.SUNDAY -> Color.parseColor("#3D28E0")
            else -> Color.parseColor("#28E0AE")
        }
    }
}
