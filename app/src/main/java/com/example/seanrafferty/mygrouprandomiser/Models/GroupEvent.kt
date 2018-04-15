package com.example.seanrafferty.mygrouprandomiser.Models

import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * data object to handle the controlling of details for a single event
 */
data class GroupEvent constructor(var ID:Int, var Date: LocalDateTime)
{
    var EventGroup : MyGroup = MyGroup()
    var EventTeams : ArrayList<Team> = arrayListOf()

    /**
     *
     */
    constructor(ID:Int, Date: LocalDateTime, Group:MyGroup, Teams:ArrayList<Team>) : this(ID, Date)
    {
        EventGroup = Group
        EventTeams = Teams
    }
}