package com.example.seanrafferty.mygrouprandomiser.Models

/**
 * Kotlin object to handle the class properties for a single group
 * @param ID: A unique ID to identify a group (read-only [val])
 * @param Name: A method to name and define a group (mutable[var] means liable to change)
 */
data class Group constructor(val ID: Int, var Name: String)
{

}