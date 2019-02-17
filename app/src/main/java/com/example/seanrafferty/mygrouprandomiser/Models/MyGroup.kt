package com.example.seanrafferty.mygrouprandomiser.Models

import androidx.room.Entity
import java.util.*

/**
 * Kotlin object to handle the class properties for a single group
 * @param ID: A unique ID to identify a group (read-only [val])
 * @param Name: A method to name and define a group (mutable[var] means liable to change)
 * @param CreationDate: Date Created for the group (defaults to current date and time)  ([var])
 */
@Entity(tableName = "GroupData")
data class MyGroup constructor(var ID: Int
                               , var Name: String
                               , var CreationDate : Date = Calendar.getInstance().getTime()
                               , var IsPrivate : Boolean = true)
{
    /**
     * A null/default object constructor
     */
    constructor() : this(-1, "")
}

