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
    lateinit var _DB : DatabaseHandler
    /**
     * constructor for player DB handler
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
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
        var AllPlayers = _DB.ReadAllPlayers() as List<Player>
        var groupPlayers = _DB.ReadAllPlayersForAGroup(group)

        //read through the list of all players
        for(item : Player in AllPlayers)
        {
            //now to filter - check if the current player is already assigned to the group!!
            var mappedPlayer = groupPlayers.filter { it.ID == item.ID }
            //if not append to the list
            if(mappedPlayer == null || mappedPlayer.isEmpty())
            {
                resultList.add(item)
            }
        }
        return resultList
    }
}