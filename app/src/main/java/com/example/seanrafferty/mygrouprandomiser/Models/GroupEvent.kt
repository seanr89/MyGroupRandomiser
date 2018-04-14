package com.example.seanrafferty.mygrouprandomiser.Models

import java.util.*

/**
 * data object to handle the controlling of details for a single event
 */
data class GroupEvent constructor(var ID:Int, var Teams:MutableList<Team>, var Group:MyGroup, var Date:Date)