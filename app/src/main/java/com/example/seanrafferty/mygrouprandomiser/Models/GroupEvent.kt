package com.example.seanrafferty.mygrouprandomiser.Models

import androidx.room.Entity
import com.example.seanrafferty.mygrouprandomiser.Models.enums.ShuffleOption
import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamStatus
import java.io.Serializable
import java.time.LocalDateTime
import kotlin.collections.ArrayList

/**
 * data object to handle the controlling of details for a single event
 */
@Entity(tableName = "Event")
data class GroupEvent constructor(var ID:Int
                                    , var Date: LocalDateTime
                                    , var GroupID: Int
                                    , var Completed: Boolean = false
                                    , var Balanced : Boolean = false
                                    , var Shuffled : ShuffleOption = ShuffleOption.UNKNOWN) : Serializable
{
    var EventTeams : ArrayList<Team> = arrayListOf()

    /**
     * Secondary Constructor
     */
    constructor(ID:Int
                , Date: LocalDateTime
                , GroupID:Int
                , Completed:Boolean
                , Balanced: Boolean
                , Teams:ArrayList<Team>) : this(ID, Date, GroupID, Completed, Balanced)
    {
        EventTeams = Teams
    }

    /**
     * Request the status of the first team of the event based on the goals scored
     * @return the team status
     */
    fun TeamOneStatus() : TeamStatus
    {
        if(EventTeams.any())
        {
            return checkTeamStatus(EventTeams[0], EventTeams[1])
        }
        return TeamStatus.UNKNOWN
    }

    /**
     * Request the status of the 2nd team of the event based on the goals scored
     * @return the team status
     */
    fun TeamTwoStatus() : TeamStatus
    {
        if(EventTeams.any())
        {
            return checkTeamStatus(EventTeams[1], EventTeams[0])
        }
        return TeamStatus.UNKNOWN
    }

    /**
     * generic method to process team status
     * @param teamToCheck : the team to run comparison to return the status
     * @param otherTeam : the team to compare against
     * @return the team status
     */
    private fun checkTeamStatus(teamToCheck: Team, otherTeam: Team) :TeamStatus
    {
        return if(teamToCheck.Score == otherTeam.Score) {
            TeamStatus.DRAW
        } else if(teamToCheck.Score > otherTeam.Score) {
            TeamStatus.WIN
        } else {
            TeamStatus.LOSS
        }
    }
}