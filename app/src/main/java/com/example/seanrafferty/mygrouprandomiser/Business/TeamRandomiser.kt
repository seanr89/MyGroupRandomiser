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
        players.sortByDescending {this.selector(it)}

        //initialise the array and two teams to store the data
        var teams = ShufflePlayersIntoTeamsByRatingAndSkills(players, CreateTeams())

        var averageDifference = teams[0].CalculateTeamPlayerAverage() - teams[1].CalculateTeamPlayerAverage()
        Log.d(TAG, "Player Average Difference is : $averageDifference")

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

/**
 * Custom class to compare teams and attempt to re-shuffle teams if necessary
 * This class will start to take into account player skill values
 * and can provide the updating of player skill values
 */
class TeamComparer
{
    private val TAG = "TeamComparer"
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
     * Process the current player and team structures to identify what team the player should be added too!
     * @param player : the player object to process
     * @param teamOne : the first team of the event/game
     * @param teamTwo : the second team of the event/game
     * @param lastSelection : the last team that a player was selected for!
     * @return the team selected for the player to be inserted into
     */
    fun runCheckOnCurrentPlayerSkillsAndReturnTeamSelect(player: Player, teamOne: Team,teamTwo: Team, lastSelection : TeamSelect) : TeamSelect
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod.name + " with lastSelection $lastSelection")

        //1. Check if the current player has any assigned skills - if not handle return selection
        if(player.skills.isEmpty())
        {
            //if(lastSelection !=UNKNOWN) return findOutWhichTeamHasTheLowestAverage(teamOne, teamTwo)

            //Log.d(object{}.javaClass.enclosingMethod.name, "player has no skills!!")
            return if(lastSelection == TEAM_ONE) TEAM_TWO
            else TEAM_ONE
        }

        var selection: TeamSelect

        //if the player only has one skill
        if(player.skills.count() == 1)
        {
            //Log.d(object{}.javaClass.enclosingMethod.name, "player has 1 skill")
            selection = decideTeamForPlayerToBeAddedForSingleSkill(player, teamOne, teamTwo, lastSelection)
            return selection
        }
        else
        {
            //2. Loop through each player skill
            //2.a generate a skill comparison
            var skillComparisons = CreateListOfShuffleComparisonsForPlayerSkills(player, teamOne, teamOne)

            //2.b identify where to add and return selection!!
            selection = processShuffleComparisonAndDecideTeamSelection(skillComparisons, lastSelection)
        }
        return selection
    }

    /**
     * Operation to create a list of custom shuffle comparison operations used when a player has multiple available skills
     * @param player :
     * @param teamOne :
     * @param teamTwo :
     * @return a collection of shuffle comparison objects
     */
    private fun CreateListOfShuffleComparisonsForPlayerSkills(player: Player, teamOne: Team,teamTwo: Team) : ArrayList<ShuffleComparisonObject>
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var resultList = arrayListOf<ShuffleComparisonObject>()

        for(item : PlayerSkill in player.skills)
        {
            var skillShuffle = ShuffleComparisonObject(item)
            skillShuffle.teamOneCount = teamOne.GetCountOfPlayersWithSkill(item)
            skillShuffle.teamTwoCount = teamTwo.GetCountOfPlayersWithSkill(item)
            skillShuffle.teamSelect = decideTeamForPlayerToBeAddedForSingleSkill(player, teamOne, teamTwo)
            if(skillShuffle.teamSelect == TEAM_ONE)
            {
                skillShuffle.updatedAverageRating = this.calculateAverageTeamRatingIfPlayerAddedToTeam(player, teamOne)
            }
            else
            {
                skillShuffle.updatedAverageRating = this.calculateAverageTeamRatingIfPlayerAddedToTeam(player, teamTwo)
            }
        }
        return resultList
    }

    /**
     * handle the processing of all the player skill shuffle options and pick the best one!
     * @param shuffleComparisons :
     * @param lastSelection :
     * @return a team selection to detail what team a player is to be added to!!
     */
    private fun processShuffleComparisonAndDecideTeamSelection(
            shuffleComparisons : ArrayList<ShuffleComparisonObject>,
            lastSelection: TeamSelect
    ) : TeamSelect
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //2. Find the item with the largest difference between team skills and average rating
        var sortedList = shuffleComparisons.sortedWith(compareBy({it.calculateDifferenceOfSkillCount()}, {it.updatedAverageRating}))
        Log.d(object{}.javaClass.enclosingMethod.name, "item selected for skill ${sortedList[0].skill.name}")

        return if(sortedList.isNotEmpty())
        //3. Find the top item and return it
            sortedList[0].teamSelect
        else
            if(lastSelection == TEAM_ONE) TEAM_TWO
            else TEAM_ONE
    }

    /*
     * select player rating content
     * @return the provided player rating (modified by skills) (used for sorting)
     */
    //private fun comparisonSelector(p: ShuffleComparisonObject): Int = p.calculateDifferenceOfSkillCount()

    /**
     * process and decide what team the player should be added two when the player has a single skill
     * @param player : the player to review and match to a team
     * @param teamOne : team one and its content
     * @param teamTwo : team two and its content
     * @param lastSelection :
     * @return an updated TeamSelect which identifies what team the player is to be added too!
     */
    private fun decideTeamForPlayerToBeAddedForSingleSkill(player: Player, teamOne: Team,teamTwo: Team, lastSelection : TeamSelect = UNKNOWN) : TeamSelect
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name + " With lastSelection $lastSelection for player ${player.Name}")
        var selection : TeamSelect

        var teamOneCount = teamOne.GetCountOfPlayersWithSkill(player.skills[0])
        var teamTwoCount = teamTwo.GetCountOfPlayersWithSkill(player.skills[0])

        Log.d(TAG, object{}.javaClass.enclosingMethod.name + "team 1 count = $teamOneCount and team 2 count = $teamTwoCount for skill ${player.skills[0].name}")

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
     * @return the TeamSelect
     */
    private fun findOutWhichTeamHasTheLowestAverage(teamOne: Team, teamTwo: Team) : TeamSelect
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

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

    /**
     * Attempt to re-calculate the team average with the provided player, but without adding the player!
     * @param player : the player to be used to re-calculate the team average
     * @param team : the team to query the current team average
     * @return a re-calculated team average rating if the player was to be added to the team
     */
    private fun calculateAverageTeamRatingIfPlayerAddedToTeam(player: Player, team: Team) : Double
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var currentTotal = 0.0
        if(team.Players.isEmpty())return currentTotal

        for (item : Player in team.Players)
        {
            currentTotal += item.skillModifiedRating()
        }
        currentTotal += player.skillModifiedRating()

        return currentTotal / (team.Players.count() + 1)
    }
}