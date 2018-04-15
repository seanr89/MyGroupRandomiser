package com.example.seanrafferty.mygrouprandomiser.Models

import java.util.*

/**
 * Kotlin object to handle the class properties for a single group
 * @param ID: A unique ID to identify a group (read-only [val])
 * @param Name: A method to name and define a group (mutable[var] means liable to change)
 * @param CreationDate: Date Created for the group (defaults to current date and time)  ([var])
 */
data class MyGroup constructor(var ID: Int, var Name: String)
{
    var CreationDate : Date = Calendar.getInstance().getTime();

    constructor(id: Int, name: String, creationDate: Date) : this(id, name)
    {
        this.ID = id
        this.Name = name
        this.CreationDate = creationDate
    }

    /**
     * A null/default object constructor
     */
    constructor() : this(-1, "")
}