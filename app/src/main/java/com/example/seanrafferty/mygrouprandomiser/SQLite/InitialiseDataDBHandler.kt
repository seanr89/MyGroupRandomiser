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

    /**
     * Initialise test groups to allow players to be assigned
     */
    private fun InitialiseDemoGroups()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)

        val myGroupDB = MyGroupDBHandler(_DB)

        myGroupDB.CreateGroup(MyGroup(-1, "Sci Park"))
        myGroupDB.CreateGroup(MyGroup(-1, "Group 1"))
        myGroupDB.CreateGroup(MyGroup(-1, "Group 3"))
        myGroupDB.CreateGroup(MyGroup(-1, "Group 4"))
    }

    /**
     * Initialise test player data set
     */
    private fun InitialiseDemoPlayers()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)

        var playerDB = PlayerDBHandler(_DB)

        playerDB.InsertPlayer(Player(0, "Sean Rafferty", 49))
        playerDB.InsertPlayer(Player(0, "Stephen Quinn", 63))
        playerDB.InsertPlayer(Player(0, "Steven Kennedy", 72))
        playerDB.InsertPlayer(Player(0, "Francy Donald", 96))
        playerDB.InsertPlayer(Player(0, "Stephen Kelso", 78))
        playerDB.InsertPlayer(Player(0, "Conor J Murphy", 80))
        playerDB.InsertPlayer(Player(0, "Chris McShane", 61))
        playerDB.InsertPlayer(Player(0, "Michael Hayes", 82))
        playerDB.InsertPlayer(Player(0, "David McCrory", 60))
        playerDB.InsertPlayer(Player(0, "William Lawrence", 73))
        playerDB.InsertPlayer(Player(0, "Sergei", 96))
        playerDB.InsertPlayer(Player(0, "James Davidson", 58))
        playerDB.InsertPlayer(Player(0, "Ryan Bevin", 73))
        playerDB.InsertPlayer(Player(0, "Mark Latten", 67))
        playerDB.InsertPlayer(Player(0, "Mark Lutton", 66))
        playerDB.InsertPlayer(Player(0, "Tommy Owens", 62))
        playerDB.InsertPlayer(Player(0, "Cormac Byrne", 60))
        playerDB.InsertPlayer(Player(0, "Emmet Mulholland", 67))
        playerDB.InsertPlayer(Player(0, "Sean Mills", 33))
        playerDB.InsertPlayer(Player(0, "Andrew Williamson", 74))
        playerDB.InsertPlayer(Player(0, "Ross Meikle", 52))
        playerDB.InsertPlayer(Player(0, "John James Fallon", 61))
        playerDB.InsertPlayer(Player(0, "Christopher Devine", 71))
        playerDB.InsertPlayer(Player(0, "Gareth Ritchie", 76))
        playerDB.InsertPlayer(Player(0, "Marcine Buczma", 50))
        playerDB.InsertPlayer(Player(0, "Anoop Andrews", 52))
        playerDB.InsertPlayer(Player(0, "Chris McGarry", 55))
    }

    /**
     * Initialise test data set of players assigned to a single group
     */
    private fun InitialiseGroupPlayerAssignment()
    {
        Log.d("InitialiseDataDBHandler", object{}.javaClass.enclosingMethod.name)
        var playerDBHandler = PlayerDBHandler(_DB)

        var players = ArrayList<Player>()
        players.add(Player(1, "", 0))
        players.add(Player(2, "", 0))
        players.add(Player(3, "", 0))
        players.add(Player(4, "", 0))
        players.add(Player(5, "", 0))
        players.add(Player(6, "", 0))
        players.add(Player(7, "", 0))
        players.add(Player(8, "", 0))
        players.add(Player(9, "", 0))
        players.add(Player(10, "", 0))
        players.add(Player(11, "", 0))
        players.add(Player(12, "", 0))
        players.add(Player(13, "", 0))
        players.add(Player(14, "", 0))
        players.add(Player(15, "", 0))
        players.add(Player(16, "", 0))
        players.add(Player(17, "", 0))
        players.add(Player(18, "", 0))
        players.add(Player(19, "", 0))
        players.add(Player(20, "", 0))
        players.add(Player(21, "", 0))
        players.add(Player(22, "", 0))
        players.add(Player(23, "", 0))
        players.add(Player(24, "", 0))
        players.add(Player(25, "", 0))
        players.add(Player(26, "", 0))
        players.add(Player(27, "", 0))
        var group = MyGroup(1, "")

        playerDBHandler.AssignPlayersToGroup(players, group)
    }
}