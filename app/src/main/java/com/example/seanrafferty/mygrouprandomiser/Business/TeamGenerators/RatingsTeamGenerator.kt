package com.example.seanrafferty.mygrouprandomiser.Business.TeamGenerators

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class RatingsTeamGenerator(context: Context?) : TeamGenerator(context) {

    private val TAG = "RatingsTeamGenerator"

    /**
     * the step to trigger the process of generating teams
     * @param players :
     * @return the teams or null!!
     */
    override fun generateTeams(players: ArrayList<Player>): ArrayList<Team> {

        if(players.isEmpty()) return null!!

        return this.shufflePlayersToTeams(players)
    }

    /**
     * implements the sorting and assigning of players to teams
     * @param players : the players to be sorted and moved to teams
     * @param teams : the teams for the players to be appended too
     */
    override fun shufflePlayersToTeams(players: ArrayList<Player>) : ArrayList<Team> {

        val teams = super.CreateTeams()
        val sortedPlayers = super.sortPlayersByRatingWithBalancing(players)

        var teamOneAdd = false
        for(item : Player in sortedPlayers)
        {
            if(!teamOneAdd)
            {
                teamOneAdd = true
                teams[0].Players.add(item)
            }
            else
            {
                teamOneAdd = false
                teams[1].Players.add(item)
            }
        }
        return teams
    }
}