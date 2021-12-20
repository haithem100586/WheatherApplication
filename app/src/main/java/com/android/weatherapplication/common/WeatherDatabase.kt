package com.android.weatherapplication.common

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.weatherapplication.data.datasource.CitiesForSearchDao
import com.android.weatherapplication.data.datasource.CurrentWeatherDao
import com.android.weatherapplication.data.datasource.ForecastDao
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.android.weatherapplication.domain.model.entity.CurrentWeatherEntity
import com.android.weatherapplication.domain.model.entity.ForecastEntity
import com.android.weatherapplication.data.Converters

@Database(
    entities = [ForecastEntity::class, CurrentWeatherEntity::class, CitiesForSearchEntity::class],
    exportSchema = false,
    version = 1
)

@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun citiesForSearchDao(): CitiesForSearchDao

    companion object {
        const val DATABASE_NAME = "weather_database"
    }
}
