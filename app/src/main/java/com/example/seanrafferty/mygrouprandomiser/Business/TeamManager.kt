package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.TeamDBHandler

class TeamManager(val context: Context?)
{

    /**
     * Operation to save a new team as a part of an event
     * @param team : operation to save/insert a new team
     * @param eventID :
     */
    fun SaveNewTeamForEvent(team:Team, eventID : Int) : Int
    {
        var dbHandler = DatabaseHandler(context)
        var teamsDB = TeamDBHandler(dbHandler)

        return teamsDB.InsertTeam(team, eventID)
    }

    /**
     * Handle the sample of players to a team created
     * @param team : the team with players assigned
     */
    fun SavePlayersToTeam(team: Team) : Int
    {
        return -1
    }

    /**
     *
     * @param team :
     * @return int
     */
    fun DeletePlayerTeamMappings(team: Team) : Int
    {
        return -1
        var dbHandler = DatabaseHandler(context)
        var teamsDB = TeamDBHandler(dbHandler)

        return teamsDB.DeletePlayerMappingsForTeam(team.ID)
    }

    /**
     * Update the known number of goals for a team
     * @param score : the number of goals scored
     * @param team : the team that scored the goals
     */
    fun UpdateTeamScore(score:Int, team: Team)
    {
        val dbHandler = DatabaseHandler(context)
        val teamsDB = TeamDBHandler(dbHandler)
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
        var teamOnePlayerIDs = teamsDB.ReadPlayerIDsForTeamID(teams[0].ID)
        var teamTwoPlayerIDs = teamsDB.ReadPlayerIDsForTeamID(teams[1].ID)

        //initialise player db handler to query the player info
        var playerDB = PlayerDBHandler(dbHandler)
        if(teamOnePlayerIDs.isNotEmpty())
        {
            teams[0].Players = playerDB.ReadAllPlayersInListOfIDs(teamOnePlayerIDs)
        }
        if(teamTwoPlayerIDs.isNotEmpty())
        {
            teams[1].Players = playerDB.ReadAllPlayersInListOfIDs(teamTwoPlayerIDs)
        }

        return teams
    }
}