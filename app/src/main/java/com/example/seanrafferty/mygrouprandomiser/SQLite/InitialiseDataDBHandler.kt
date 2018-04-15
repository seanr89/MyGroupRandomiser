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
    }

    private fun InitialiseDemoGroups()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)

        _DB.CreateGroup(MyGroup(-1, "Randox IT"))
        _DB.CreateGroup(MyGroup(-1, "Randox Eng"))
        _DB.CreateGroup(MyGroup(-1, "Group 3"))
    }

    private fun InitialiseDemoPlayers()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)

        _DB.InsertPlayer(Player(0, "Sean Rafferty", 5))
        _DB.InsertPlayer(Player(0, "Stephen Quinn", 7))
        _DB.InsertPlayer(Player(0, "Steven Kennedy", 8))
        _DB.InsertPlayer(Player(0, "Francy Donald", 10))
        _DB.InsertPlayer(Player(0, "Stephen Kelso", 9))
    }
}