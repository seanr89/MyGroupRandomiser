package com.example.seanrafferty.mygrouprandomiser.SQLite.Databases

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.Database
import android.arch.persistence.room.DatabaseConfiguration
import android.arch.persistence.room.InvalidationTracker
import android.arch.persistence.room.RoomDatabase
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DataAccessorObjects.PlayerDao


/**
 * Attempting to create the ROOM Player Database
 */
@Database(entities = arrayOf(Player::class), version = 1)
abstract class PlayerDatabase : RoomDatabase()
{
    abstract fun playerDao(): PlayerDao



    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}