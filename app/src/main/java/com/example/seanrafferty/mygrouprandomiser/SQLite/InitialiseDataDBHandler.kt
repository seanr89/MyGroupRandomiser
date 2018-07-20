package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill

class InitialiseDataDBHandler
{
    var _DB : DatabaseHandler
    private val TAG = "InitialiseDataDBHandler"
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
        InitialiasePlayerSkills()
        InitialisePlayerMappingsToSkills()
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

        playerDB.InsertPlayer(Player(0, "Sean Rafferty", 49.0)) //1
        playerDB.InsertPlayer(Player(0, "Stephen Quinn", 63.0))
        playerDB.InsertPlayer(Player(0, "Steven Kennedy", 72.0))
        playerDB.InsertPlayer(Player(0, "Francy Donald", 96.0))
        playerDB.InsertPlayer(Player(0, "Stephen Kelso", 78.0)) //5
        playerDB.InsertPlayer(Player(0, "Conor J Murphy", 80.0))
        playerDB.InsertPlayer(Player(0, "Chris McShane", 61.0))
        playerDB.InsertPlayer(Player(0, "Michael Hayes", 82.0))
        playerDB.InsertPlayer(Player(0, "David McCrory", 60.0))
        playerDB.InsertPlayer(Player(0, "William Lawrence", 73.0))//10
        playerDB.InsertPlayer(Player(0, "Sergei", 96.0))
        playerDB.InsertPlayer(Player(0, "James Davidson", 58.0))
        playerDB.InsertPlayer(Player(0, "Ryan Bevin", 73.0))
        playerDB.InsertPlayer(Player(0, "Mark Latten", 67.0))
        playerDB.InsertPlayer(Player(0, "Mark Lutton", 66.0))//15
        playerDB.InsertPlayer(Player(0, "Tommy Owens", 62.0))
        playerDB.InsertPlayer(Player(0, "Cormac Byrne", 60.0))
        playerDB.InsertPlayer(Player(0, "Emmet Mulholland", 67.0))
        playerDB.InsertPlayer(Player(0, "Sean Mills", 33.0))
        playerDB.InsertPlayer(Player(0, "Andrew Williamson", 74.0)) //20
        playerDB.InsertPlayer(Player(0, "Ross Meikle", 52.0))
        playerDB.InsertPlayer(Player(0, "John James Fallon", 61.0))
        playerDB.InsertPlayer(Player(0, "Christopher Devine", 71.0))
        playerDB.InsertPlayer(Player(0, "Gareth Ritchie", 76.0))
        playerDB.InsertPlayer(Player(0, "Marcine Buczma", 50.0))//25
        playerDB.InsertPlayer(Player(0, "Anoop Andrews", 52.0))
        playerDB.InsertPlayer(Player(0, "Chris McGarry", 55.0))
    }

    /**
     * Initialise test data set of players assigned to a single group
     */
    private fun InitialiseGroupPlayerAssignment()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var playerDBHandler = PlayerDBHandler(_DB)

        var players = ArrayList<Player>()
        players.add(Player(1, "", 0.0))
        players.add(Player(2, "", 0.0))
        players.add(Player(3, "", 0.0))
        players.add(Player(4, "", 0.0))
        players.add(Player(5, "", 0.0))
        players.add(Player(6, "", 0.0))
        players.add(Player(7, "", 0.0))
        players.add(Player(8, "", 0.0))
        players.add(Player(9, "", 0.0))
        players.add(Player(10, "", 0.0))
        players.add(Player(11, "", 0.0))
        players.add(Player(12, "", 0.0))
        players.add(Player(13, "", 0.0))
        players.add(Player(14, "", 0.0))
        players.add(Player(15, "", 0.0))
        players.add(Player(16, "", 0.0))
        players.add(Player(17, "", 0.0))
        players.add(Player(18, "", 0.0))
        players.add(Player(19, "", 0.0))
        players.add(Player(20, "", 0.0))
        players.add(Player(21, "", 0.0))
        players.add(Player(22, "", 0.0))
        players.add(Player(23, "", 0.0))
        players.add(Player(24, "", 0.0))
        players.add(Player(25, "", 0.0))
        players.add(Player(26, "", 0.0))
        players.add(Player(27, "", 0.0))
        var group = MyGroup(1, "")

        playerDBHandler.AssignPlayersToGroup(players, group)
    }

    /**
     * Initialise a list of all default skills that a player can maintain
     */
    private fun InitialiasePlayerSkills()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var db = PlayerSkillDBHandler(_DB)

        db.InsertPlayerSkill(PlayerSkill(0, "Runner", 1.6))
        db.InsertPlayerSkill(PlayerSkill(0, "Goal Scorer", 1.8))
        db.InsertPlayerSkill(PlayerSkill(0, "Defensive", 1.3))
        db.InsertPlayerSkill(PlayerSkill(0, "Keeper", 1.1))
        db.InsertPlayerSkill(PlayerSkill(0, "Play Maker", 1.5))
        db.InsertPlayerSkill(PlayerSkill(0, "Poacher", 1.4))
        //db.InsertPlayerSkill(PlayerSkill(0, "Poacher", 1.4))
    }

    /**
     * Initialise test data to map players to there respective skills
     */
    private fun InitialisePlayerMappingsToSkills()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var db = PlayerDBHandler(_DB)

        //Sean
        db.InsertPlayerSkillToPlayer(Player(1, ""), PlayerSkill(1))
        //Dave
        db.InsertPlayerSkillToPlayer(Player(9, ""), PlayerSkill(4))
        //Andy
        db.InsertPlayerSkillToPlayer(Player(20, ""), PlayerSkill(1))
        //Francy
        db.InsertPlayerSkillToPlayer(Player(4, ""), PlayerSkill(1))
        db.InsertPlayerSkillToPlayer(Player(4, ""), PlayerSkill(2))
        //Sergei
        db.InsertPlayerSkillToPlayer(Player(11, ""), PlayerSkill(2))
        //Michael Hayes
        db.InsertPlayerSkillToPlayer(Player(8, ""), PlayerSkill(5))
        //Chris McShane
        db.InsertPlayerSkillToPlayer(Player(7, ""), PlayerSkill(3))
        //Conor
        db.InsertPlayerSkillToPlayer(Player(6, ""), PlayerSkill(1))
        //Emmet
        db.InsertPlayerSkillToPlayer(Player(18, ""), PlayerSkill(1))

    }
}