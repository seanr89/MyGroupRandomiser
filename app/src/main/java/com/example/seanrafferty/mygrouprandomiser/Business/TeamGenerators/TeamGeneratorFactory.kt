package com.example.seanrafferty.mygrouprandomiser.Business.TeamGenerators

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Business.Interfaces.ITeamGenerator
import com.example.seanrafferty.mygrouprandomiser.Models.enums.ShuffleOption
import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamSelect

class TeamGeneratorFactory {

    fun getGenerator(option : ShuffleOption, context: Context?) : ITeamGenerator{

        when(option){
            ShuffleOption.RANDOM -> {
                return TeamGenerator(context)
            }
            ShuffleOption.RATING -> {
                return RatingsTeamGenerator(context)
            }
            ShuffleOption.RATING_SKILL -> {
                return RatingAndSkillTeamGenerator(context)
            }
        }
        return TeamGenerator(context)
    }
}