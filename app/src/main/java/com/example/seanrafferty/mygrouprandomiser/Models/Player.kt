package com.example.seanrafferty.mygrouprandomiser.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/*
https://medium.com/mindorks/android-architecture-components-room-and-kotlin-f7b725c8d1d
 */

/**
 * Serializable - Player object model used to store and access individual player data
 * @param ID : unique player identifier used in data store
 * @param Name : The name of the player
 * @param Rating : how skillful is the player (rated 1 too 10 with 10 being highest rating)
 */
@Entity(tableName = "PlayerData")
data class Player constructor(@PrimaryKey(autoGenerate = true)var ID: Int
                              , @ColumnInfo(name = "name") var Name: String
                              , @ColumnInfo(name = "rating")var Rating: Int) : Serializable
{
}