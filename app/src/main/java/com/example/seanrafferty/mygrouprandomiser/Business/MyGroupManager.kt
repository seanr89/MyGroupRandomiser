package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.MyGroupDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler

/**
 * Provide business logic and layer to communicate between presentation and data layers
 * @param context : Activity Context to allow DBHelper integration
 */
class MyGroupManager(val context: Context?)
{

    /**
     * New method to move events to business layer from datalayer
     */
    fun ReadAllPlayersForGroup(group: MyGroup) : ArrayList<Player>
    {
        var groupDB = MyGroupDBHandler(DatabaseHandler(context))
        return groupDB.ReadAllPlayersForAGroup(group)
    }

    /**
     * Query all players that are not assigned to a the current group
     * @param group : the selected group
     * @return : a list of Player objects
     */
    fun ReadAllPlayersNotAssignedToGroup(group : MyGroup) : List<Player>
    {
        Log.d("MyGroupManager", object{}.javaClass.enclosingMethod.name)

        //Create and initialise a list
        var resultList : MutableList<Player> = arrayListOf()

        var playerDB = PlayerDBHandler(DatabaseHandler(context))

        //request all players and all players for the current group
        var AllPlayers = playerDB.ReadAllPlayers()
        var groupPlayers = ReadAllPlayersForGroup(group)

        //read through the list of all players
        for(item : Player in AllPlayers)
        {
            //now to filter - check if the current player is already assigned to the group!!
            //if current player(item) is in list of group players
            var mappedPlayer = groupPlayers.filter { it.ID == item.ID }
            //if not append to the list
            if(mappedPlayer == null || mappedPlayer.isEmpty())
            {
                resultList.add(mappedPlayer[0])
            }
        }
        return resultList
    }
}