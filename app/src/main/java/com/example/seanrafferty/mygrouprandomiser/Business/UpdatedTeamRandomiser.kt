package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class UpdatedTeamRandomiser(val context: Context?)
{
    private val TAG = "UpdatedTeamRandomiser"
    private val UnevenModifier = 1.4

    fun RandomizePlayerListIntoTeams(players:ArrayList<Player>) : ArrayList<Team>
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //Check if the players list is empty first
        if(players.isEmpty()) {
            return null!!
        }
        //initialise the array and two teams
        var teams = CreateTeams()
        return teams
    }

    /**
     *
     */
    private fun isPlayerCountBalanced(players: ArrayList<Player>) : Boolean
    {
        if(!players.isEmpty())
        {
            if(players.size % 2 == 0)
            {
                return true
            }
        }
        return false
    }


    /**
     * Simple internal method to create an array list with two team objects provided
     * @return A list that contains two teams
     */
    private fun CreateTeams() : ArrayList<Team>
    {
        val teamOne = Team(0, "Team One", 0)
        val teamTwo = Team(0, "Team Two", 0)

        var teams = ArrayList<Team>()
        teams.add(teamOne)
        teams.add(teamTwo)
        return teams
    }

    /**
     * select player rating content
     * @param p : an individual player object
     * @return the provided player rating (modified by skills) (used for sorting)
     */
    private fun selector(p: Player): Double = p.skillModifiedRating()
}