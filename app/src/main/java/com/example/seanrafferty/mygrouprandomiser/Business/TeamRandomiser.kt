package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
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
     *
     * @param players :
     * @return collection of teams with full sorting!
     */
    fun RandomizePlayersAndSortTeamsByRatingAndSkills(players: ArrayList<Player>) : ArrayList<Team>
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //Check if the players list is empty first
        if(players.isEmpty())
            return null!!

        //first sort the player list by rating (now by the skill modified rating)
        players.sortByDescending({this.selector(it)})

        //initialise the array and two teams to store the data
        var teams = ShufflePlayersIntoTeamsByRatingAndSkills(players, CreateTeams())

        var averageDifference = teams[0].CalculateTeamPlayerAverage() - teams[1].CalculateTeamPlayerAverage()
        Log.d(TAG, "Player Average Difference is : $averageDifference")

        return teams
    }

    /**
     * Handle shuffling of players based on rating and ensure skills are not one sided
     */
    private fun ShufflePlayersIntoTeamsByRatingAndSkills(players : ArrayList<Player>, teams : ArrayList<Team>) : ArrayList<Team>
    {
        //init param to id if the last player was added to the first team!!
        var currentSelect = TeamSelect.UNKNOWN

        //Initialise the team comparer object
        var comparer = TeamComparer(context)
        for(item : Player in players)
        {
            var currentSelect = comparer.runCheckOnCurrentPlayerSkillsAndReturnTeamSelect(item, teams[0], teams[1], currentSelect)
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

        var Teams = ArrayList<Team>()
        Teams.add(teamOne)
        Teams.add(teamTwo)

        return Teams!!
    }

    /**
     * select player rating content
     * @return the provided player rating (modified by skills) (used for sorting)
     */
    private fun selector(p: Player): Double = p.skillModifiedRating()

}

/**
 * Custom class to compare teams and attempt to re-shuffle teams if necessary
 * This class will start to take into account player skill values
 * and can provide the updating of player skill values
 */
class TeamComparer
{
    var skills : ArrayList<PlayerSkill> = arrayListOf()

    /**
     * constructor with initialisation request for a all player skills
     */
    constructor(context: Context?)
    {
        var playerManager = PlayerManager(context)
        skills = playerManager.ReadAllAvailablePlayerSkills()
    }

    /**
     *
     */
    fun runCheckOnCurrentPlayerSkillsAndReturnTeamSelect(player: Player, teamOne: Team,teamTwo: Team, lastSelection : TeamSelect) : TeamSelect
    {
        Log.d("TeamComparer", object{}.javaClass.enclosingMethod.name)

        //1. Check if the current player has any assigned skills - if not handle return selection
        if(player.skills.isEmpty())
        {
            return if(lastSelection == TEAM_ONE) TEAM_TWO
            else TEAM_ONE
        }

        var selection = UNKNOWN

        if(player.skills.count() == 1)
        {
            selection = decideTeamForPlayerToBeAddedForSingleSkill(player, teamOne, teamTwo, lastSelection)
            return selection
        }
        else
        {
            //2. Loop through each player skill
            for(item : PlayerSkill in player.skills)
            {

            }
        }



        return selection
    }

    private fun decideTeamForPlayerToBeAddedForSingleSkill(player: Player, teamOne: Team,teamTwo: Team, lastSelection : TeamSelect) : TeamSelect
    {
        var selection = UNKNOWN

        var teamOneCount = teamOne.GetCountOfPlayersWithSkill(player.skills[0])
        var teamTwoCount = teamTwo.GetCountOfPlayersWithSkill(player.skills[0])

        //if neither team has that selection - then assign a default
        if(teamOneCount == 0 && teamTwoCount == 0)
        {
            selection = if(lastSelection == TEAM_ONE) TEAM_TWO
            else TEAM_ONE
        }
        else if(teamOneCount > teamTwoCount)
        {
            //add to team two!!
            selection = TEAM_TWO
        }
        else if(teamOneCount < teamTwoCount)
        {
            //add to team one
            selection = TEAM_ONE
        }
        else //if the two are not 0 but are equal
        {
            selection = findOutWhichTeamHasTheLowestAverage(teamOne, teamTwo)
        }

        return selection
    }


    /**
     * calculate which team has the lowest average value and return the selection
     * @param teamOne :
     * @param teamTwo :
     */
    private fun findOutWhichTeamHasTheLowestAverage(teamOne: Team, teamTwo: Team) : TeamSelect
    {
        Log.d("TeamComparer", object{}.javaClass.enclosingMethod.name)

        return when {
            teamOne.CalculateTeamPlayerAverage() < teamTwo.CalculateTeamPlayerAverage() -> {
                TEAM_ONE
            }
            teamOne.CalculateTeamPlayerAverage() > teamTwo.CalculateTeamPlayerAverage() -> {
                TEAM_TWO
            }
            else -> {
                if(teamOne.Players.count() > teamTwo.Players.count()) TEAM_TWO
                else TEAM_ONE
            }
        }
    }

    private fun calculateAverageTeamRatingIfPlayerAddedToTeam(player: Player, team: Team) : Double
    {

        return 0.0
    }
}