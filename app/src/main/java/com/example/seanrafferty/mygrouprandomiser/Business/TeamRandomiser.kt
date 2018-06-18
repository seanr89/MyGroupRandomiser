package com.example.seanrafferty.mygrouprandomiser.Business

import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

/**
 * Provides business logic for randomisation of players into teams
 */
class TeamRandomiser
{
    private val TAG = "TeamRandomiser"
    /**
     * Shuffle the players provided and append to two teams (one and two) and return the teams as a collection
     * @param players : a collection of players
     */
    fun RandomizePlayerListIntoTeams(players:ArrayList<Player>) : ArrayList<Team>
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //Check if the players list is empty first
        if(players.isEmpty())
            return null!!

        //Shuffle the player list
        var shuffledList = players.toMutableList().shuffled() as ArrayList<Player>

        //initialise the array and two teams
        var teams = CreateTeams()

        teams = ShuffleTeamsBasic(shuffledList, teams)

        var averageDifference = teams[0].CalculateTeamPlayerAverage() - teams[1].CalculateTeamPlayerAverage()
        Log.d(TAG, "Player Average Difference is : $averageDifference")

        return teams
    }

    /**
     * Handle the generation of teams and player selection based on player ratings
     * by sorting the players by rating and distributing evenly
     * @param players : a collection of players to sort
     * @return a collection of team objects : with sorted players
     */
    fun RandomizePlayerListBySortedRating(players: ArrayList<Player>) : ArrayList<Team>
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //Check if the players list is empty first
        if(players.isEmpty())
            return null!!

        //first sort the player list by rating
        players.sortByDescending({this.selector(it)})

        //initialise the array and two teams
        var teams = CreateTeams()

        teams = ShuffleTeamsBasic(players, teams)

        var averageDifference = teams[0].CalculateTeamPlayerAverage() - teams[1].CalculateTeamPlayerAverage()
        Log.d(TAG, "Player Average Difference is : $averageDifference")

        return teams
    }

    /**
     * select player rating content
     * @return the provided player rating (used for sorting)
     */
    private fun selector(p: Player): Int = p.Rating

    /**
     * Handle the basic shuffling and sorting of teams (i.e. read through loop provided)
     * @param players
     * @param teams
     * @return array of teams with players added into each team
     */
    private fun ShuffleTeamsBasic(players : ArrayList<Player>, teams : ArrayList<Team>) : ArrayList<Team>
    {
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
     * Simple internal method to create an array list with two team objects provided
     * @return A list that contains two teams
     */
    private fun CreateTeams() : ArrayList<Team>
    {
        var teamOne = Team(0, "Team One", 0)
        var teamTwo = Team(0, "Team Two", 0)

        var Teams = ArrayList<Team>()
        Teams.add(teamOne)
        Teams.add(teamTwo)

        return Teams!!
    }
}