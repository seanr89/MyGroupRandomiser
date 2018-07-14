package com.example.seanrafferty.mygrouprandomiser.Models

import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamSelect

/**
 * Custom object to store base data for each player skill in order to define where a player should be situated
 * @param skill : the skill identified to use
 * @param teamOneCount : the count of players with the same skill in team 1
 * @param teamTwoCount : the count of players with the same skill in team 2
 * @param updatedAverageRating : the updated average rating of the team if the player was to be added!
 * @param teamSelect : the team identified to be added too
 */
class ShuffleComparisonObject(val skill: PlayerSkill,
                              var teamOneCount : Int,
                              var teamTwoCount : Int,
                              var updatedAverageRating : Double,
                              var teamSelect: TeamSelect)
{
    constructor(singleSkill : PlayerSkill) : this(singleSkill, 0,0,0.0, TeamSelect.UNKNOWN)
}