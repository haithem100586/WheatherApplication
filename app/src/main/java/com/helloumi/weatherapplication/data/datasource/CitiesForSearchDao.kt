package com.helloumi.weatherapplication.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity

@Dao
interface CitiesForSearchDao : BaseDao<CitiesForSearchEntity> {

    @Query("SELECT * FROM CitiesForSearch")
    fun getCities(): LiveData<List<CitiesForSearchEntity>>

    @Query("SELECT * FROM CitiesForSearch WHERE fullName like '%' || :city || '%'|| '%' ORDER BY fullName DESC")
    fun getCityByName(city: String? = ""): LiveData<List<CitiesForSearchEntity>>

    @Query("DELETE FROM CitiesForSearch")
    suspend fun deleteCities()

    @Query("Select count(*) from CitiesForSearch")
    suspend fun getCount(): Int
}
