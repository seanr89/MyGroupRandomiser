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
     * Handle the creation of a new MyGroup and save to the database
     * @param group : the group to save
     * @return the ID of the newly inserted row, or -1 if an error occurred
     */
    fun CreateGroup(group : MyGroup) : Int
    {
        var MyGroupDB = MyGroupDBHandler(DatabaseHandler(context))
        return MyGroupDB.CreateGroup(group)
    }

    /**
     * Operation to request all available groups stored
     * @return an ArrayList of MyGroup objects
     */
    fun ReadAllGroups() : ArrayList<MyGroup>
    {
        var MyGroupDB = MyGroupDBHandler(DatabaseHandler(context))
        return MyGroupDB.ReadAllGroups()
    }

    /**
     * Operation to read and return an individual group by an ID
     * @param id :
     * @return the group or null
     */
    fun ReadGroupByID(id : Int) : MyGroup
    {
        var MyGroupDB = MyGroupDBHandler(DatabaseHandler(context))
        return MyGroupDB.ReadMyGroupByID(id)
    }

    /**
     * New method to move events to business layer from database
     * @param group : the group to request players for
     * @return a filtered arraylist of players assigned to a group
     */
    fun ReadAllPlayersForGroup(group: MyGroup) : ArrayList<Player>
    {
        var groupDB = MyGroupDBHandler(DatabaseHandler(context))
        var playerManager = PlayerManager(context)

        var modelList: MutableList<Player> = mutableListOf()

        //Get all of the player IDs
        var playerIDs = groupDB.ReadAllPlayerIDsForGroup(group)

        //Get all Players
        var players = playerManager.ReadAllPlayers()

        if(playerIDs.isNotEmpty())
        {
            for(item: Int in playerIDs)
            {
                var player = players.filter { it.ID == item }
                if(player != null)
                {
                    modelList.add(player[0])
                }
            }
        }
        else
        {
            Log.d("TAG", "No Player IDs found")
        }
        return modelList as ArrayList<Player>
    }

    /**
     * Query all players that are not assigned to a the current group
     * @param group : the selected group used to filter
     * @return : a list of Player objects filtered to not already be present in the group
     */
    fun ReadAllPlayersNotAssignedToGroup(group : MyGroup) : List<Player>
    {
        //Create and initialise a list
        var resultList : MutableList<Player> = arrayListOf()

        var playerDB = PlayerDBHandler(DatabaseHandler(context))

        //request all players and all players for the current group
        var allPlayers = playerDB.ReadAllPlayers()

        var groupPlayers = ReadAllPlayersForGroup(group)

        if(groupPlayers.isEmpty())
            return allPlayers

        if(allPlayers.isNotEmpty())
        {
            //if no players assigned to the group - just return all players!
            if(groupPlayers.isEmpty())
            {
                return allPlayers
            }
            //read through the list of all players
            for(item : Player in allPlayers)
            {
                //now to filter - check if the current player is already assigned to the group!!
                //if current player(item) is in list of group players
                var mappedPlayer = groupPlayers.filter { it.ID == item.ID }
                //if not append to the list
                if(mappedPlayer.isEmpty())
                {
                    resultList.add(item)
                }
            }
        }
        return resultList
    }
}