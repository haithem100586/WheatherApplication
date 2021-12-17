package com.android.weatherapplication.domain.model.entity

import android.os.Parcelable
import androidx.room.*
import com.android.weatherapplication.domain.model.ForecastResponse
import com.android.weatherapplication.domain.model.ListItem
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "Forecast")
data class ForecastEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @Embedded
    var city: CityEntity?,

    @ColumnInfo(name = "list")
    var list: List<ListItem>?
) : Parcelable {

    @Ignore
    constructor(forecastResponse: ForecastResponse) : this(
        id = 0,
        city = forecastResponse.city?.let { CityEntity(it) },
        list = forecastResponse.list
    )
}
