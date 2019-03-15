package com.example.seanrafferty.mygrouprandomiser.Business.TeamGenerators

import com.example.seanrafferty.mygrouprandomiser.Business.Interfaces.ITeamGenerator
import com.example.seanrafferty.mygrouprandomiser.Models.enums.ShuffleOption
import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamSelect

class TeamGeneratorFactory {

    fun getGenerator(option : ShuffleOption) : ITeamGenerator{

        when(option){
            ShuffleOption.RANDOM -> {
                return TeamGenerator()
            }
            ShuffleOption.RATING -> {
                return RatingsTeamGenerator()
            }
            ShuffleOption.RATING_SKILL -> {
                return RatingAndSkillTeamGenerator()
            }
        }
        return TeamGenerator()
    }
}