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
}