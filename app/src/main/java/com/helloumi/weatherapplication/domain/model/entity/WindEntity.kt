package com.helloumi.weatherapplication.domain.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.helloumi.weatherapplication.domain.model.Wind
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Wind")
data class WindEntity(
    @ColumnInfo(name = "deg")
    val deg: Double?,
    @ColumnInfo(name = "speed")
    val speed: Double?
) : Parcelable
