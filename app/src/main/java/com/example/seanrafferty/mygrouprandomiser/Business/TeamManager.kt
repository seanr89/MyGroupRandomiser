package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.TeamDBHandler

class TeamManager(val context: Context?)
{

    /**
     * Update the known number of goals for a team
     * @param score : the number of goals scored
     * @param team : the team that scored the goals
     */
    fun UpdateTeamScore(score:Int, team: Team)
    {
        var dbHandler = DatabaseHandler(context)
        var teamsDB = TeamDBHandler(dbHandler)

        teamsDB.UpdateTeamScore(team.ID, score)
    }

    /**
     * Read all teams for an individual event
     * @param eventID : event ID to filter team options
     * @return an arraylist of teams with players
     */
    fun ReadTeamsForEvent(eventID : Int) : ArrayList<Team>
    {
        var dbHandler = DatabaseHandler(context)
        var teamsDB = TeamDBHandler(dbHandler)

        var teams = teamsDB.ReadTeamsForEvent(eventID)

        //now to communicate the get the players for each

        return teams
    }
}