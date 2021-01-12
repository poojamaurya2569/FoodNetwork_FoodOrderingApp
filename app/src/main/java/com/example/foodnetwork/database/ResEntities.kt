package com.example.foodnetwork.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class ResEntities(@PrimaryKey val resId :Int,
                       @ColumnInfo(name = "resName") val resName: String,
                       @ColumnInfo(name = "resRating")val resRating: String,
                       @ColumnInfo(name = "resPrice") val resPrice: String,
                       @ColumnInfo(name = "resImg") val  resImg: String)