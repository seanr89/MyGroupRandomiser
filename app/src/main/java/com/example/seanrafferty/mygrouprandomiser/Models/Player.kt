package com.example.seanrafferty.mygrouprandomiser.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/*
https://www.youtube.com/watch?v=H7I3zs-L-1w
https://medium.com/mindorks/android-architecture-components-room-and-kotlin-f7b725c8d1d
 */

/**
 * Serializable - Player object model used to store and access individual player data
 * @param ID : unique player identifier used in data store
 * @param Name : The name of the player
 * @param Rating : how skillful is the player (rated 1 too 100 with 100 being highest rating)
 * @param isPrivate : defaulted to true
 * @param isActive : defaulted to true
 * @param skills : Array list of potential skills for each player
 */
@Entity(tableName = "PlayerData")
data class Player constructor(@PrimaryKey(autoGenerate = true)var ID: Int
                                , @ColumnInfo(name = "name") var Name: String
                                , @ColumnInfo(name = "rating")var Rating: Double
                                , @ColumnInfo(name = "isPrivate")var isPrivate : Boolean = true
                                , @ColumnInfo(name = "isActive")var isActive : Boolean = true
                                , var skills : ArrayList<PlayerSkill>) : Serializable
{
    constructor (id : Int, name : String, rating : Double = 0.0) : this(id,name,rating, true, true, arrayListOf())

    /**
     * new method to combine and return the total playerSkill modifier values
     * @return double value - defaulted to 1.0
     */
    private fun playerSkillModifierCombined() : Double
    {
        var result = 1.0
        if(skills.isEmpty()) return result

        for(item : PlayerSkill in skills)
        {
            result += item.modifier
        }
        return result
    }

    /**
     * calculation to return an updated and modified rating based on the PlayerSkills
     * @return double or 1.0 as default
     */
    fun skillModifiedRating() : Double
    {
        return Rating * playerSkillModifierCombined()
    }

    /**
     * Operation to query if the player has a specific skill assigned
     * @param skill : the skill to query (based on name)
     * @return Boolean paramater - true if the player has the assigned skill
     */
    fun doesPlayerHaveSkill(skill: PlayerSkill) : Boolean
    {
        var result = false

        if(this.skills.isEmpty())
        {
            return result
        }

        if(this.skills.filter { s -> s.name == skill.name }.count() == 1)
        {
            result = true
        }

        return result
    }
}