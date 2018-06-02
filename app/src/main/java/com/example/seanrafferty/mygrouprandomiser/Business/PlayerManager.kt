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

    /**
     * Request all players
     * @return an ArrayList of players
     */
    fun ReadAllPlayers() : ArrayList<Player>
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        return db.ReadAllPlayers()
    }

    /**
     * Insert a new player into the database
     * @param player : the player to be inserted
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun SavePlayer(player : Player) : Int
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        return db.InsertPlayer(player)
    }
}