package com.example.seanrafferty.mygrouprandomiser.Business.TeamGenerators

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Business.Interfaces.ITeamGenerator
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

open class TeamGenerator: ITeamGenerator {

    private val TAG = "TeamGenerator"
    protected var cont : Context?

    constructor(context: Context?){
        cont = context
    }

    override fun generateTeams(players: ArrayList<Player>): ArrayList<Team> {

        Log.d(TAG, object{}.javaClass.enclosingMethod!!.name)
        //Check if the players list is empty first
        if(players.isEmpty()) {
            return null!!
        }
        //Shuffle the player list
        val  shuffledList = players.toMutableList().shuffled() as ArrayList<Player>

        val teams = this.shufflePlayersToTeams(shuffledList)
        return teams
    }

    /**
     * handle the shuffling of players into teams
     */
    override fun shufflePlayersToTeams(players: ArrayList<Player>) : ArrayList<Team> {

        //initialise the array and two teams
        var teams = this.CreateTeams()

        var teamOneAdd = false
        for(item : Player in players)
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

    /**
     * check if the number of players is event
     * @param players : arraylist of players
     * @return : Boolean parameter
     */
    override fun isPlayerCountBalanced(players: ArrayList<Player>) : Boolean
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
    protected fun CreateTeams() : ArrayList<Team>
    {
        val teamOne = Team(0, "Team One", 0)
        val teamTwo = Team(0, "Team Two", 0)

        var teams = ArrayList<Team>()
        teams.add(teamOne)
        teams.add(teamTwo)
        return teams
    }

    /**
     * handle the sorting of players based on the base rating with skill modifier
     * @param players :
     * @returns sorted list of players
     */
    protected fun sortPlayersByRatingWithBalancing(players: ArrayList<Player>) : ArrayList<Player>
    {
        //if the player count is not balanced
        if(!this.isPlayerCountBalanced(players))
        {
            players.sortBy { this.selector(it) }
        }
        else
        {
            //first sort the player list by rating
            players.sortByDescending {this.selector(it)}
        }
        return players
    }

    /**
     * select player rating content
     * @param p : an individual player object
     * @return the provided player rating (modified by skills) (used for sorting)
     */
    protected fun selector(p: Player): Double = p.skillModifiedRating()
}