package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler

/**
 * Provide business logic and layer to communicate between presentation and data layers
 * @param context : Activity Context to allow DBHelper integration
 */
class PlayerManager(val context: Context?)
{

    fun ReadAllPlayers() : ArrayList<Player>
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        return db.ReadAllPlayers()
    }
}