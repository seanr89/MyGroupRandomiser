package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
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
     * Read all stored players and return
     * @return ArrayList of players
     */
    fun ReadAllPlayers() : ArrayList<Player>
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList = ArrayList<Player>()

        // Select All Query
        var selectQuery: String = "SELECT * FROM ${DatabaseHandler.PlayerTable}"
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
     * Query all players that are not assigned to a the current group
     * @param group : the selected group
     * @return : a list of Player objects
     */
    fun GetAllPlayersNotAssignedToGroup(group:MyGroup) : List<Player>
    {
        Log.d("PlayerDBHandler", object{}.javaClass.enclosingMethod.name)

        //Create and initialise a list
        var resultList : MutableList<Player> = arrayListOf()

        //request all players and all players for the current group
        var AllPlayers = ReadAllPlayers()
        Log.d("PlayerDBHandler", "AllPlayers list: ${AllPlayers.size}")

        /*var groupPlayers = _DB.ReadAllPlayersForAGroup(group)
        Log.d("PlayerDBHandler", "groupPlayers list: ${groupPlayers.size}")

        //read through the list of all players
        for(item : Player in AllPlayers)
        {
            //now to filter - check if the current player is already assigned to the group!!
            //if current player(item) is in list of group players
            var mappedPlayer = groupPlayers.filter { it.ID == item.ID }
            //if not append to the list
            if(mappedPlayer == null || mappedPlayer.isEmpty())
            {
                Log.d(object{}.javaClass.enclosingMethod.name, "Adding player ${mappedPlayer[0].Name}")
                resultList.add(mappedPlayer[0])
            }
        }*/
        return resultList
    }

    /**
     * Insert a new player
     * @param player - Player object to be inserted
     * @return int
     */
    fun InsertPlayer(player: Player) : Int
    {
        Log.d("PlayerDBHandler", object{}.javaClass.enclosingMethod.name)

        var values = ContentValues()
        values.put(DatabaseHandler.PlayerName, player.Name)
        values.put(DatabaseHandler.PlayerRating, player.Rating)

        val db = _DB.GetWritableDataBaseObject()

        return db!!.insert(DatabaseHandler.PlayerTable, "", values).toInt()
    }
}