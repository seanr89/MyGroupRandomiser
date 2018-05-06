package com.example.seanrafferty.mygrouprandomiser.Models

import java.io.Serializable

/**
 * Data object to detail the players of a team
 * @param ID - Unique ID of the Team
 * @param Players - A collection of the Player objects
 */
data class Team constructor(var ID:Int, var Name:String, var Score:Int, var Players:ArrayList<Player>) : Serializable
{
    constructor(ID: Int, Name:String, Score:Int) : this(ID, Name, Score, ArrayList())
}