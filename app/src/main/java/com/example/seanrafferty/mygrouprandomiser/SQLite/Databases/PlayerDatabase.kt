package com.example.seanrafferty.mygrouprandomiser.SQLite.Databases

import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
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