package com.example.seanrafferty.mygrouprandomiser.Models

import android.arch.persistence.room.Entity
import java.io.Serializable

/**
 * Data object to detail the players of a team
 * @param ID - Unique ID of the Team
 * @param Score : the number of goals scored by the team (default = 0)
 * @param Players - A collection of the Player objects
 */
@Entity(tableName = "Team")
data class Team constructor(var ID:Int, var Name:String, var Score:Int = 0, var Players:ArrayList<Player>) : Serializable
{
    /**
     * default constructor to initialise player list
     */
    constructor(ID: Int, Name:String, Score:Int) : this(ID, Name, Score, ArrayList())


    /**
     * calculate the average rating of the the players in the included team
     * will check for players
     * @return 0.0 as default or the average rating of the players
     */
    fun CalculateTeamPlayerAverage() : Double
    {
        var result = 0.0
        if(this.Players != null && this.Players.isNotEmpty())
        {
            val ratings = this.Players.map { it.Rating }
            result = ratings.average()
        }
        return result
    }
}

enum class TeamStatus
{
    WIN,
    LOSS,
    DRAW,
    UNKNOWN
}