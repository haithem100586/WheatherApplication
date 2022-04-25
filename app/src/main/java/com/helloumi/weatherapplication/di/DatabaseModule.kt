package com.helloumi.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.helloumi.weatherapplication.common.WeatherDatabase
import com.helloumi.weatherapplication.common.ext.koin.foreground
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    foreground {
        scoped { createDatabase(androidContext()) }
    }
}

///////////////////////////////////////////////////////////////////////////
// Internal
///////////////////////////////////////////////////////////////////////////

/**
 * Creates application database.
 *
 * @param context context.
 */
private fun createDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        WeatherDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()
