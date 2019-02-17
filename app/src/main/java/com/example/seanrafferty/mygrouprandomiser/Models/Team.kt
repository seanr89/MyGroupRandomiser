package com.example.seanrafferty.mygrouprandomiser.Models

import androidx.room.Entity
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

    /**
     * Operation to get query the number of players in the team with the queried skill
     * @param skill :
     * @return the count of players who have the identified skill
     */
    fun GetCountOfPlayersWithSkill(skill: PlayerSkill) : Int
    {
        var result = 0

        if(this.Players.isEmpty()) return result

        for(item : Player in this.Players)
        {
            if(item.doesPlayerHaveSkill(skill))
            {
                result += 1
            }
        }
        return result
    }
}