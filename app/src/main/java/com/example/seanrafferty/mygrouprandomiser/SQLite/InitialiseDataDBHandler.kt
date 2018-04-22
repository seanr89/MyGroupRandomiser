package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player

class InitialiseDataDBHandler
{
    var _DB : DatabaseHandler
    /**
     * constructor for player DB handler
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    fun RunDataInitialisation()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)
        InitialiseDemoGroups()
        InitialiseDemoPlayers()
        InitialiseGroupPlayerAssignment()
    }

    private fun InitialiseDemoGroups()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)

        _DB.CreateGroup(MyGroup(-1, "Randox IT"))
        _DB.CreateGroup(MyGroup(-1, "Randox Eng"))
        _DB.CreateGroup(MyGroup(-1, "Group 3"))
        _DB.CreateGroup(MyGroup(-1, "Group 4"))
        _DB.CreateGroup(MyGroup(-1, "Group 5"))
        _DB.CreateGroup(MyGroup(-1, "Group 6"))

    }

    private fun InitialiseDemoPlayers()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)

        _DB.InsertPlayer(Player(0, "Sean Rafferty", 5))
        _DB.InsertPlayer(Player(0, "Stephen Quinn", 7))
        _DB.InsertPlayer(Player(0, "Steven Kennedy", 8))
        _DB.InsertPlayer(Player(0, "Francy Donald", 10))
        _DB.InsertPlayer(Player(0, "Stephen Kelso", 9))
        _DB.InsertPlayer(Player(0, "Conor J Murphy", 9))
        _DB.InsertPlayer(Player(0, "Chris McShane", 7))
        _DB.InsertPlayer(Player(0, "Michael Hayes", 8))
        _DB.InsertPlayer(Player(0, "Sergei", 9))
        _DB.InsertPlayer(Player(0, "James Davidson", 7))
        _DB.InsertPlayer(Player(0, "Ryan Bevin", 8))
        _DB.InsertPlayer(Player(0, "Sean Mills", 3))
    }

    private fun InitialiseGroupPlayerAssignment()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)
        var PlayerDBHandler = PlayerDBHandler(_DB)

        var players = ArrayList<Player>()
        players.add(Player(1, "", 0))
        players.add(Player(2, "", 0))
        players.add(Player(3, "", 0))
        players.add(Player(4, "", 0))
        var group = MyGroup(1, "")

        PlayerDBHandler.AssignPlayersToGroup(players, group)
    }
}