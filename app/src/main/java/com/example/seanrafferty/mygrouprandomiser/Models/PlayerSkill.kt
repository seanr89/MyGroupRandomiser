package com.example.seanrafferty.mygrouprandomiser.Models

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * object to provide a method to detail a particular skill of a player
 * @param id : unique of the of skill
 * @param name : the name/description of the skill
 * @param modifier : the modification calculation to update the players rating
 */
@Entity(tableName = "PlayerSkill")
data class PlayerSkill(@PrimaryKey(autoGenerate = true)var id : Int
                       , var name : String
                       , var modifier : Double)
{
    constructor(id:Int) : this(id, "",0.0)
}