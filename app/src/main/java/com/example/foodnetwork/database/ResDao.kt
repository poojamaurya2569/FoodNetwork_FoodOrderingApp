package com.example.foodnetwork.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResDao {
    @Insert
    fun insertRes (resEntities: ResEntities)

     @Delete
    fun deleteRes (resEntities: ResEntities)

    @Query("SELECT * FROM restaurants")
    fun getAllRes():List<ResEntities>


    @Query("SELECT * FROM restaurants WHERE resId = :ResId")
    fun getResById(ResId: String): ResEntities
}