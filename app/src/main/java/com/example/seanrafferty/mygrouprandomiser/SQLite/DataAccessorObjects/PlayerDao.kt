package com.example.seanrafferty.mygrouprandomiser.SQLite.DataAccessorObjects

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Update
import com.example.seanrafferty.mygrouprandomiser.Models.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = REPLACE)
    fun insert(player: Player) : Int

    @Update
    fun update(player : Player) : Int

    @Delete
    fun delete(player : Player) : Int
}