package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.entities.City

@Dao
interface CityDao {

    @Query("SELECT * from city_table ORDER BY id ASC")
    fun getAllCities(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Update
    suspend fun updateCity(city: City)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()

}