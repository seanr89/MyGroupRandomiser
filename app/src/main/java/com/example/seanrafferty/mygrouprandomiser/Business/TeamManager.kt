package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.TeamDBHandler

class TeamManager(val context: Context?)
{

    /**
     *
     */
    fun UpdateTeamScore(score:Int, team: Team)
    {

    }

    /**
     * Read all teams for an individual event
     * @param eventID : event ID to filter team options
     * @return an arraylist of teams
     */
    fun ReadTeamsForEvent(eventID : Int) : ArrayList<Team>
    {
        var dbHandler = DatabaseHandler(context)
        var teamsDB = TeamDBHandler(dbHandler)

        //now to communicate the get the players for each

        return teamsDB.ReadTeamsForEvent(eventID)
    }
}