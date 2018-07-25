package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.Models.ShuffleComparisonObject
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamSelect
import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamSelect.*

/**
 * Provides business logic for randomisation of players into teams
 */
class TeamRandomiser(val context: Context?)
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

        //first sort the player list by rating (now by the skill modified rating)
        players.sortByDescending({this.selector(it)})

        //initialise the array and two teams
        var teams = CreateTeams()
        teams = ShuffleTeamsBasic(players, teams)

        var averageDifference = teams[0].CalculateTeamPlayerAverage() - teams[1].CalculateTeamPlayerAverage()
        Log.d(TAG, "Player Average Difference is : $averageDifference")

        return teams
    }

    /**
     * Handle the basic shuffling and sorting of teams (i.e. read through loop provided)
     * @param players :  arraylist of players to shuffle
     * @param teams : the arraylist of two teams expected to append players too!
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
     * Updated randomization/shuffling process that will use a combination of player ratings and skills
     * @param players : the list of players to sort and append into teams
     * @return collection of teams with full sorting!
     */
    fun RandomizePlayersAndSortTeamsByRatingAndSkills(players: ArrayList<Player>) : ArrayList<Team>
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //Check if the players list is empty first
        if(players.isEmpty())
            return null!!

        //first sort the player list by rating (now by the skill modified rating)
        players.sortBy {this.selector(it)}

        //initialise the array and two teams to store the data
        var teams = ShufflePlayersIntoTeamsByRatingAndSkills(players, CreateTeams())

        var averageDifference = teams[0].CalculateTeamPlayerAverage() - teams[1].CalculateTeamPlayerAverage()
        //Log.d(TAG, "Player Average Difference is : $averageDifference")

        return teams
    }

    /**
     * Handle shuffling of players based on rating and ensure skills are not one sided
     * @param players : An arraylist of players to sort based on rating and skills
     * @param teams : And arraylist of teams that players are to be assigned too!
     * @return and arraylist of teams with players assigned
     */
    private fun ShufflePlayersIntoTeamsByRatingAndSkills(players : ArrayList<Player>, teams : ArrayList<Team>) : ArrayList<Team>
    {
        //init param to id if the last player was added to the first team!!
        var currentSelect = TeamSelect.UNKNOWN

        //Initialise the team comparer object
        var comparer = TeamComparer(context)
        for(item : Player in players)
        {
            currentSelect = comparer.runCheckOnCurrentPlayerSkillsAndReturnTeamSelect(item, teams[0], teams[1], currentSelect)
            Log.d("Select", "Select team $currentSelect for player ${item.Name}")
            when(currentSelect)
            {
                TEAM_ONE -> {
                    teams[0].Players.add(item)
                }
                TEAM_TWO -> {
                    teams[1].Players.add(item)
                }
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

        var teams = ArrayList<Team>()
        teams.add(teamOne)
        teams.add(teamTwo)

        return teams!!
    }

    /**
     * select player rating content
     * @return the provided player rating (modified by skills) (used for sorting)
     */
    private fun selector(p: Player): Double = p.skillModifiedRating()

}