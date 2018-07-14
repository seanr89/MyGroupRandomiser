package com.example.seanrafferty.mygrouprandomiser.Models

import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamSelect

/**
 * Custom object to store base data for each player skill in order to define where a player should be situated
 * @param skill :
 * @param teamOneCount :
 * @param teamTwoCount :
 * @param updatedAverageRating :
 */
class ShuffleComparisonObject(val skill: PlayerSkill, val teamOneCount : Int, val teamTwoCount : Int, val updatedAverageRating : Double)
{
}