package com.example.seanrafferty.mygrouprandomiser.Business.TeamGenerators

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Business.Sorters.PlayerSorter
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class RatingAndSkillTeamGenerator(context: Context?) : TeamGenerator(context) {

    private val TAG = "RatingAndSkillTeamGenerator"

    /**
     * the step to trigger the process of generating teams
     * @param players :
     * @return the teams or null!!
     */
    override fun generateTeams(players: ArrayList<Player>): ArrayList<Team> {
        Log.d(TAG, object{}.javaClass.enclosingMethod!!.name)

        if(players.isEmpty()) return null!!

        val teams = this.shufflePlayersToTeams(players)
        return teams
    }

    /**
     * TODO
     */
    override fun shufflePlayersToTeams(players: ArrayList<Player>)  : ArrayList<Team> {
        Log.d(TAG, object{}.javaClass.enclosingMethod!!.name)

        var teams = super.CreateTeams()
        val sortedPlayers = super.sortPlayersByRatingWithBalancing(players)

        //We now need the comparison object to run here!
        val sorter = PlayerSorter(super.cont)

        return sorter.sortPlayersIntoTeams(sortedPlayers, teams)
    }
}