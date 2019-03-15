package com.example.seanrafferty.mygrouprandomiser.Business.Interfaces

import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

/**
 * interface design for multiple instances of custom team generation
 */
interface ITeamGenerator {

    /**
     * defines the method to generate the teams based on the randomisation process
     */
    fun generateTeams(players:ArrayList<Player>) : ArrayList<Team>

    /**
     * defines the parameter check to see if the playercount is even/balanced
     */
    fun isPlayerCountBalanced(players: ArrayList<Player>) : Boolean

    /**
     * defined method to handle the shuffling of teams
     */
    fun shufflePlayersToTeams(players : ArrayList<Player>, teams : ArrayList<Team>)
}