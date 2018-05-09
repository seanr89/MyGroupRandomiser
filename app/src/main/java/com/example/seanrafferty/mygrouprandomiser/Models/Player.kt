package com.example.seanrafferty.mygrouprandomiser.Models

import java.io.Serializable

/**
 * Serializable - Player object model used to store and access individual player data
 * @param ID : unique player identifier used in data store
 * @param Name : The name of the player
 * @param Rating : how skillful is the player (rated 1 too 10 with 10 being highest rating)
 */
data class Player constructor(var ID: Int, var Name: String, var Rating: Int) : Serializable
{
}