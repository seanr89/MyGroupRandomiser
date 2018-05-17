package com.example.seanrafferty.mygrouprandomiser.Models

import android.arch.persistence.room.Entity
import java.io.Serializable

/**
 * Data object to detail the players of a team
 * @param ID - Unique ID of the Team
 * @param Score : the number of goals scored by the team (default = 0)
 * @param Players - A collection of the Player objects
 */
@Entity(tableName = "TeamData")
data class Team constructor(var ID:Int, var Name:String, var Score:Int = 0, var Players:ArrayList<Player>) : Serializable
{
    /**
     * default constructor to initialise player list
     */
    constructor(ID: Int, Name:String, Score:Int) : this(ID, Name, Score, ArrayList())
}