package com.example.foodnetwork.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [ResEntities::class],version = 1)
abstract class ResDatabase: RoomDatabase() {
    abstract fun resDao():ResDao

}