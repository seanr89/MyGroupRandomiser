package com.example.seanrafferty.mygrouprandomiser.Business.TeamGenerators

import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class RatingsTeamGenerator : TeamGenerator() {

    private val TAG = "RatingsTeamGenerator"

    /**
     * the step to trigger the process of generating teams
     * @param players :
     * @return the teams or null!!
     */
    override fun generateTeams(players: ArrayList<Player>): ArrayList<Team> {

        if(players.isEmpty()) return null!!

        val teams = super.CreateTeams()
        this.shufflePlayersToTeams(players, teams)
        return teams
    }

    /**
     * implements the sorting and assigning of players to teams
     * @param players : the players to be sorted and moved to teams
     * @param teams : the teams for the players to be appended too
     */
    override fun shufflePlayersToTeams(players: ArrayList<Player>, teams: ArrayList<Team>) {

        val sortedPlayers = this.sortPlayersWithBalancing(players)

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
    }

    /**
     * handle the sorting of players based on the base rating with skill modifier
     * @param players :
     * @returns sorted list of players
     */
    private fun sortPlayersWithBalancing(players: ArrayList<Player>) : ArrayList<Player>
    {
        //if the player count is not balanced
        if(!super.isPlayerCountBalanced(players))
        {
            players.sortBy { super.selector(it) }
        }
        else
        {
            //first sort the player list by rating
            players.sortByDescending {super.selector(it)}
        }
        return players
    }
}