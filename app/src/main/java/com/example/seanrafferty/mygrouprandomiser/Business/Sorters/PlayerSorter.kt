package com.example.seanrafferty.mygrouprandomiser.Business.Sorters

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Business.PlayerManager
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.Models.Team

/**
 * new base sorter for player content
 */
class PlayerSorter(context: Context?) {
    private val TAG = "PlayerSorter"
    var skills : ArrayList<PlayerSkill> = arrayListOf()

    init {
        val playerManager = PlayerManager(context)
        skills = playerManager.ReadAllAvailablePlayerSkills()
    }

    /**
     * ok plan for the method is a few fold
     * 1.
     * 2.
     * 3.
     */
    fun sortPlayersIntoTeams(players: ArrayList<Player>, teams: ArrayList<Team>) : ArrayList<Team>{
        //TODO not implemented
        return null!!
    }
}