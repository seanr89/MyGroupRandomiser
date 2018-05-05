package com.example.seanrafferty.mygrouprandomiser.Models

import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * data object to handle the controlling of details for a single event
 */
data class GroupEvent constructor(var ID:Int, var Date: LocalDateTime, var GroupID: Int, var Completed: Boolean = false)
{
    var EventTeams : ArrayList<Team> = arrayListOf()

    /**
     * Secondary Constructor
     */
    constructor(ID:Int, Date: LocalDateTime, GroupID:Int, Completed:Boolean, Teams:ArrayList<Team>) : this(ID, Date, GroupID, Completed)
    {
        EventTeams = Teams
    }
}