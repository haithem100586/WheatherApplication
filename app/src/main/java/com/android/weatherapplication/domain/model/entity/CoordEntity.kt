package com.android.weatherapplication.domain.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.android.weatherapplication.domain.model.Geoloc
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Coord")
data class CoordEntity(
    @ColumnInfo(name = "lon")
    val lon: Double?,
    @ColumnInfo(name = "lat")
    val lat: Double?
) : Parcelable {

    @Ignore
    constructor(geoloc: Geoloc?) : this(
        lon = geoloc?.lng,
        lat = geoloc?.lat
    )
}
