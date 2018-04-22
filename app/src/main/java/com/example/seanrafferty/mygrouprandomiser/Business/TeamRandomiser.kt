package com.example.seanrafferty.mygrouprandomiser.Business

import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

/**
 * Provides business logic for randomisation of players into teams
 */
class TeamRandomiser
{
    /**
     * Shuffle the players provided and append to two teams (one and two) and return the teams as a collection
     * @param players : a collection of players
     */
    fun RandomizePlayerListIntoTeams(players:ArrayList<Player>) : ArrayList<Team>
    {
        //Check if the players list is empty first
        if(players.isEmpty())
            return null!!

        var shuffledList = players.toMutableList().shuffle() as ArrayList<Player>
        var teamOne = Team(0, "Team One")
        var teamTwo = Team(0, "Team Two")

        var teamOneAdd : Boolean  = false
        for(item : Player in shuffledList)
        {
            if(!teamOneAdd)
            {
                teamOneAdd = true
                teamOne.Players.add(item)
            }
            else
            {
                teamOneAdd = false
                teamTwo.Players.add(item)
            }
        }

        var Teams = ArrayList<Team>()
        Teams.add(teamOne)
        Teams.add(teamTwo)

        return Teams
    }


}