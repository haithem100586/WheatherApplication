package com.android.weatherapplication.domain.model.entity

import android.os.Parcelable
import android.text.SpannableString
import androidx.room.*
import com.android.weatherapplication.domain.model.HitsItem
import com.android.weatherapplication.utils.extensions.bold
import com.android.weatherapplication.utils.extensions.italic
import com.android.weatherapplication.utils.extensions.plus
import com.android.weatherapplication.utils.extensions.spannable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "CitiesForSearch")
data class CitiesForSearchEntity(
    @ColumnInfo(name = "administrative")
    val administrative: String?,
    @ColumnInfo(name = "Country")
    val country: String?,
    @Embedded
    val coord: CoordEntity?,
    @ColumnInfo(name = "fullName")
    val name: String?,
    @ColumnInfo(name = "county")
    val county: String?,
    @ColumnInfo(name = "importance")
    val importance: Int?,
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String
) : Parcelable {
    @Ignore
    constructor(hitsItem: HitsItem?) : this(
        country = hitsItem?.country,
        importance = hitsItem?.importance,
        administrative = hitsItem?.administrative?.first(),
        coord = CoordEntity(hitsItem?.geoloc),
        name = hitsItem?.localeNames?.first(),
        county = hitsItem?.county?.first(),
        id = hitsItem?.objectID.toString()
    )

    constructor(cityId: String, cityName: String) : this(
        id = cityId,
        name = cityName,

        country = null,
        importance = null,
        administrative = null,
        coord = null,
        county = null,
    )

    fun getFullName(): SpannableString {
        return spannable {
            bold(name ?: "").plus(", ") +
                    bold(county ?: "").plus(", ") +
                    italic(administrative ?: "").plus(", ") +
                    italic(country ?: "")
        }
    }
}
