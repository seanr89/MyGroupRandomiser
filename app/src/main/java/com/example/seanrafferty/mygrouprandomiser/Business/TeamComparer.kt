package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.Models.ShuffleComparisonObject
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamSelect


/**
 * Compare teams and attempt to re-shuffle teams if necessary
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
    fun runCheckOnCurrentPlayerSkillsAndReturnTeamSelect(player: Player,
                                                         teamOne: Team,
                                                         teamTwo: Team,
                                                         lastSelection : TeamSelect) : TeamSelect
    {

        //1. Check if the current player has any assigned skills - if not handle return selection
        if(player.skills.isEmpty())
        {
            //Log.d(object{}.javaClass.enclosingMethod.name, "player has no skills!!")
            return if(lastSelection == TeamSelect.TEAM_ONE) TeamSelect.TEAM_TWO
            else TeamSelect.TEAM_ONE
        }

        var selection: TeamSelect

        //if the player only has one skill
        if(player.skills.count() == 1)
        {
            selection = decideTeamForPlayerToBeAddedForSingleSkill(player,
                    teamOne,
                    teamTwo,
                    lastSelection)
            return selection
        }
        else
        {
            //2 generate a skill comparison
            var skillComparisons = CreateListOfShuffleComparisonsForPlayerSkills(player, teamOne, teamOne)

            //3 identify where to add and return selection!!
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
    private fun CreateListOfShuffleComparisonsForPlayerSkills(player: Player,
                                                              teamOne: Team,
                                                              teamTwo: Team)
            : ArrayList<ShuffleComparisonObject>
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var resultList = arrayListOf<ShuffleComparisonObject>()

        for(item : PlayerSkill in player.skills)
        {
            var skillShuffle = ShuffleComparisonObject(item)
            skillShuffle.teamOneCount = teamOne.GetCountOfPlayersWithSkill(item)
            skillShuffle.teamTwoCount = teamTwo.GetCountOfPlayersWithSkill(item)
            skillShuffle.teamSelect = decideTeamForPlayerToBeAddedForSingleSkill(player, teamOne, teamTwo)


            if(skillShuffle.teamSelect == TeamSelect.TEAM_ONE)
            {
                skillShuffle.updatedAverageRating = this.calculateAverageTeamRatingIfPlayerAddedToTeam(player, teamOne)
            }
            else
            {
                skillShuffle.updatedAverageRating = this.calculateAverageTeamRatingIfPlayerAddedToTeam(player, teamTwo)
            }
            resultList.add(skillShuffle)
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
        Log.d(TAG, object{}.javaClass.enclosingMethod.name + "with shuffle count : ${shuffleComparisons.count()}")

        //1. Find the item with the largest difference between team skills and average rating
        var sortedList = shuffleComparisons.sortedWith(compareBy({it.calculateDifferenceOfSkillCount()}, {it.updatedAverageRating}))

        return when {
            sortedList.isNotEmpty()
                //2. Find the top item and return it
            -> sortedList[0].teamSelect
            lastSelection == TeamSelect.TEAM_ONE -> TeamSelect.TEAM_TWO
            else -> TeamSelect.TEAM_ONE
        }
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
    private fun decideTeamForPlayerToBeAddedForSingleSkill(player: Player, teamOne: Team, teamTwo: Team, lastSelection : TeamSelect = TeamSelect.UNKNOWN) : TeamSelect
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name + " With lastSelection $lastSelection for player ${player.Name}")
        var selection : TeamSelect

        var teamOneCount = teamOne.GetCountOfPlayersWithSkill(player.skills[0])
        var teamTwoCount = teamTwo.GetCountOfPlayersWithSkill(player.skills[0])

        Log.d(TAG, object{}.javaClass.enclosingMethod.name + "team 1 count = $teamOneCount and team 2 count = $teamTwoCount for skill ${player.skills[0].name}")

        //if neither team has that selection - then assign a default
        if(teamOneCount == 0 && teamTwoCount == 0)
        {
            selection = if(lastSelection == TeamSelect.TEAM_ONE) TeamSelect.TEAM_TWO
            else TeamSelect.TEAM_ONE
        }
        else if(teamOneCount > teamTwoCount)
        {
            //add to team two!!
            selection = TeamSelect.TEAM_TWO
        }
        else if(teamOneCount < teamTwoCount)
        {
            //add to team one
            selection = TeamSelect.TEAM_ONE
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
                TeamSelect.TEAM_ONE
            }
            teamOne.CalculateTeamPlayerAverage() > teamTwo.CalculateTeamPlayerAverage() -> {
                TeamSelect.TEAM_TWO
            }
            else -> {
                if(teamOne.Players.count() > teamTwo.Players.count()) TeamSelect.TEAM_TWO
                else TeamSelect.TEAM_ONE
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

    /**
     * Test method to review if the team numbers or in balanced
     * @return true if the difference is out by more than 1
     */
    private fun areTeamNumbersUnEven(teamOne: Team, teamTwo: Team) : Boolean
    {
        return false
    }
}