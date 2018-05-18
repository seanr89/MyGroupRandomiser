package com.example.seanrafferty.mygrouprandomiser.SQLite.DataAccessorObjects

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.example.seanrafferty.mygrouprandomiser.Models.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = REPLACE)
    fun insert(player: Player)
}