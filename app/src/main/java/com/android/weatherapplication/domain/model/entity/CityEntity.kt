package com.android.weatherapplication.domain.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import com.android.weatherapplication.domain.model.City
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "City")
data class CityEntity(
    @ColumnInfo(name = "cityCountry")
    var cityCountry: String?,
    @Embedded
    var cityCoord: CoordEntity?,
    @ColumnInfo(name = "cityName")
    var cityName: String?,
    @ColumnInfo(name = "cityId")
    var cityId: Int?
) : Parcelable {

    @Ignore
    constructor(city: City) : this(
        cityId = city.id,
        cityCoord = city.coord?.let { CoordEntity(it) },
        cityName = city.name,
        cityCountry = city.country
    )

    fun getCityAndCountry(): String {
        return if (cityCountry.equals("none"))
            "$cityName"
        else
            "$cityName, $cityCountry"
    }
}
