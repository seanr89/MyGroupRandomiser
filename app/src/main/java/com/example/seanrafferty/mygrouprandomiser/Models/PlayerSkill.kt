package com.example.seanrafferty.mygrouprandomiser.Models

import android.arch.persistence.room.Entity


/**
 * object to provide a method to detail a particular skill of a player
 * @param id : unique of the of skill
 * @param name : the name/description of the skill
 * @param modifier : the modification calculation to update the players rating
 */
@Entity(tableName = "PlayerSkill")
data class PlayerSkill(var id : Int, var name : String, var modifier : Double) {
}