package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player

/**
Database Handler relating solely to Player info
 */
class PlayerDBHandler
{
    var _DB : DatabaseHandler

    /**
     * constructor for player DB handler
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    /**
     * Read all stored players
     * @return ArrayList of players
     */
    fun ReadAllPlayers() : ArrayList<Player>
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        var arrayList = ArrayList<Player>()

        // Select All Query
        var selectQuery = "SELECT * FROM ${DatabaseHandler.PlayerTable}"
        val db = _DB.readableDatabase

        var cursor = db!!.rawQuery(selectQuery, null)
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerpkID))
                    val name =cursor.getString(cursor.getColumnIndex(DatabaseHandler.PlayerName))
                    val rating = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerRating))

                    arrayList.add(Player(id, name, rating))
                }
                while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList
    }

    /**
     * Read all available players from the list of player IDs
     * @param playerIDs : the list of playerIDs to read player info for
     * @return a list of players
     */
    fun ReadAllPlayersInListOfIDs(playerIDs : ArrayList<Int>) : ArrayList<Player>
    {
        var arrayList = ArrayList<Player>()

        //combine all of the player IDs from the list to a comma separated string
        var combinedString = playerIDs.joinToString(",", "", "")

        // Select All Query
        var selectQuery = "SELECT * FROM ${DatabaseHandler.PlayerTable} WHERE ${DatabaseHandler.PlayerpkID} IN ($combinedString)"
        val db = _DB.readableDatabase

        var cursor: Cursor?
        try {
            cursor = db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            return null!!
        }

        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerpkID))
                    val name =cursor.getString(cursor.getColumnIndex(DatabaseHandler.PlayerName))
                    val rating = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerRating))

                    arrayList.add(Player(id, name, rating))
                }
                while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList
    }

    /**
     * Insert and Assign player list to a group
     * @param playerList : the list of players to assign to a group
     * @param group : the group to assign these players
     *
     */
    fun AssignPlayersToGroup(playerList: MutableList<Player>, group:MyGroup)
    {
        Log.d("PlayerDBHandler", object{}.javaClass.enclosingMethod.name)

        //Ensure database handler is writable
        var db = _DB.GetWritableDataBaseObject()

        for(item: Player in playerList)
        {
            var values = ContentValues()
            values.put(DatabaseHandler.PlayerID, item.ID)
            values.put(DatabaseHandler.GroupID, group.ID)

            db!!.insert(DatabaseHandler.GroupPlayerTable, "", values).toInt()
        }
    }

    /**
     * Insert a new player
     * @param player - Player object to be inserted
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    @Throws(SQLiteException::class)
    fun InsertPlayer(player: Player) : Int
    {
        Log.d("PlayerDBHandler", object{}.javaClass.enclosingMethod.name)

        var values = ContentValues()
        values.put(DatabaseHandler.PlayerName, player.Name)
        values.put(DatabaseHandler.PlayerRating, player.Rating)

        val db = _DB.GetWritableDataBaseObject()

        return try {
            db!!.insert(DatabaseHandler.PlayerTable, "", values).toInt()
        }
        catch (e: SQLiteException)
        {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            -1
        }
    }
}