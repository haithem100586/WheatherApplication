package com.android.weatherapplication.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity

@Dao
interface CitiesForSearchDao {

    @Query("SELECT * FROM CitiesForSearch")
    fun getCities(): LiveData<List<CitiesForSearchEntity>>

    @Query("SELECT * FROM CitiesForSearch WHERE fullName like '%' || :city || '%'|| '%' ORDER BY fullName DESC")
    fun getCityByName(city: String? = ""): LiveData<List<CitiesForSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(citiesForSearchEntity: CitiesForSearchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CitiesForSearchEntity>)

    @Query("DELETE FROM CitiesForSearch")
    suspend fun deleteCities()

    @Query("Select count(*) from CitiesForSearch")
    suspend fun getCount(): Int
}
