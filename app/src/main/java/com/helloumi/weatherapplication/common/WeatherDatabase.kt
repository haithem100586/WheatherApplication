package com.helloumi.weatherapplication.common

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.helloumi.weatherapplication.data.datasource.CitiesForSearchDao
import com.helloumi.weatherapplication.data.datasource.CurrentWeatherDao
import com.helloumi.weatherapplication.data.datasource.ForecastDao
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.domain.model.entity.CurrentWeatherEntity
import com.helloumi.weatherapplication.domain.model.entity.ForecastEntity
import com.helloumi.weatherapplication.data.Converters

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
