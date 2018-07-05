package com.example.seanrafferty.mygrouprandomiser.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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
 * @param isPrivate : defaulted to false
 * @param skills : Array list of potential skills for each player
 */
@Entity(tableName = "PlayerData")
data class Player constructor(@PrimaryKey(autoGenerate = true)var ID: Int
                                , @ColumnInfo(name = "name") var Name: String
                                , @ColumnInfo(name = "rating")var Rating: Double
                                , @ColumnInfo(name = "isPrivate")var isPrivate : Boolean = false
                                , var skills : ArrayList<PlayerSkill>) : Serializable
{

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
        var result = 1.0

        result = Rating * playerSkillModifierCombined()

        return result;
    }
}