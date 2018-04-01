package com.example.seanrafferty.mygrouprandomiser.Models

import java.util.*

/**
 * Kotlin object to handle the class properties for a single group
 * @param ID: A unique ID to identify a group (read-only [val])
 * @param Name: A method to name and define a group (mutable[var] means liable to change)
 * @param CreationDate: Date Created for the group (defaults to current date and time)  ([var])
 */
data class Group constructor(var ID: Int, var Name: String)
{
//    var ID : Int = 0
//    var Name : String? = null
    var CreationDate : Date = Calendar.getInstance().getTime();

//    constructor(id: Int, name: String)
//    {
//        this.ID = id
//        this.Name = name
//    }
}