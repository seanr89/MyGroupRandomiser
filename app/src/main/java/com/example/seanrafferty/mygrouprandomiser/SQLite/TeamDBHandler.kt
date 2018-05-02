package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class TeamDBHandler
{
    var _DB : DatabaseHandler
    /**
     * constructor for player DB handler
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    /**
     *
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun CreateTeam(team : Team, eventID: Int) : Int
    {
        var result : Int

        var db = _DB.GetWritableDataBaseObject()

        var values = ContentValues()
        values.put(DatabaseHandler.TeamName, team.Name)
        values.put(DatabaseHandler.TeamEventID, eventID)

        result = db!!.insert(DatabaseHandler.TeamTable, "", values).toInt()

        return result
    }

    /**
     *
     */
    fun CreateTeamPlayerMapping(teamID : Int, playerID : Int) : Int
    {
        var result : Int

        var db = _DB.GetWritableDataBaseObject()

        var values = ContentValues()
        values.put(DatabaseHandler.TeamID, teamID)
        values.put(DatabaseHandler.PlayerID, playerID)

        result = db!!.insert(DatabaseHandler.TeamPlayerMappingTable, "", values).toInt()

        return result
    }

    fun ReadTeamsForEvent(event:GroupEvent)
    {

    }
}