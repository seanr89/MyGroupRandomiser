package com.example.seanrafferty.mygrouprandomiser.Models

import android.arch.persistence.room.Entity
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * data object to handle the controlling of details for a single event
 */
@Entity(tableName = "Event")
data class GroupEvent constructor(var ID:Int
                                  , var Date: LocalDateTime
                                  , var GroupID: Int
                                  , var Completed: Boolean = false
                                  , var Balanced : Boolean = false) : Serializable
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

    fun TeamOneStatus() : TeamStatus
    {
        if(EventTeams.any())
        {
            return if(EventTeams[0].Score == EventTeams[1].Score) {
                TeamStatus.DRAW
            } else if(EventTeams[0].Score > EventTeams[1].Score) {
                TeamStatus.WIN
            } else {
                TeamStatus.LOSS
            }
        }

        return TeamStatus.UNKNOWN
    }

    fun TeamTwoStatus() : TeamStatus
    {
        if(EventTeams.any())
        {
            return if(EventTeams[0].Score == EventTeams[1].Score) {
                TeamStatus.DRAW
            } else if(EventTeams[1].Score > EventTeams[2].Score) {
                TeamStatus.WIN
            } else {
                TeamStatus.LOSS
            }
        }

        return TeamStatus.UNKNOWN
    }
}