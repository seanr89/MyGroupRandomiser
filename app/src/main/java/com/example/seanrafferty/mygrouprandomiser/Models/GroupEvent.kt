package com.example.seanrafferty.mygrouprandomiser.Models

import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * data object to handle the controlling of details for a single event
 */
data class GroupEvent constructor(var ID:Int, var Date: LocalDateTime, var GroupID: Int)
{
    var EventTeams : ArrayList<Team> = arrayListOf()

    /**
     * Secondary Constructor
     */
    constructor(ID:Int, Date: LocalDateTime, GroupID:Int, Teams:ArrayList<Team>) : this(ID, Date, GroupID)
    {
        EventTeams = Teams
    }
}