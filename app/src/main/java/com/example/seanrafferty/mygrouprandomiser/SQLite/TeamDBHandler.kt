package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class TeamDBHandler
{
    var _DB : DatabaseHandler
    /**
     * constructor for player DB handler
     * @param db : database handler to handle sqlite operations
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    /**
     * Save the team to the database
     * @param team : populated team object
     * @param eventID :
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun InsertTeam(team : Team, eventID: Int) : Int
    {
        var result : Int

        var db = _DB.GetWritableDataBaseObject()

        var values = ContentValues()
        values.put(DatabaseHandler.TeamName, team.Name)
        values.put(DatabaseHandler.TeamEventID, eventID)
        values.put(DatabaseHandler.TeamScore, 0)

        result = db!!.insert(DatabaseHandler.TeamTable, "", values).toInt()

        return result
    }

    /**
     * Save the player to the current team in the database as a mapping
     * @param teamID : team identifier
     * @param playerID : player identifier
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

    /**
     * Handle the deletion/removal of a
     */
    fun DeletePlayerFromTeam(teamID: Int, playerID: Int) : Int
    {
        var result = 0
        var db = _DB.GetWritableDataBaseObject()

        return try {
            result = db!!.delete(DatabaseHandler.TeamPlayerMappingTable, "${DatabaseHandler.TeamID}=? AND ${DatabaseHandler.PlayerID}=?"
                    , arrayOf(Integer.toString(teamID),Integer.toString(playerID)))
            db.close()
            result
        }
        catch(e: SQLiteException)
        {
            -1
        }
    }

    /**
     *
     */
    fun DeletePlayerMappingsForTeam(teamID: Int) :Int
    {
        var result = 0
        var db = _DB.GetWritableDataBaseObject()

        return try {
            result = db!!.delete(DatabaseHandler.TeamPlayerMappingTable, "${DatabaseHandler.TeamID}=?", arrayOf(Integer.toString(teamID)))
            db.close()
            result
        }
        catch(e: SQLiteException)
        {
            -1
        }
    }

    /**
     * Read the teams for a single event
     * @param eventID : the ID of the event
     * @param list of teams for an event (minus players)
     */
    fun ReadTeamsForEvent(eventID:Int) : ArrayList<Team>
    {
        var db = _DB.GetReadableDataBaseObject()
        var arrayList = ArrayList<Team>()

        var selectQuery = "SELECT * FROM ${DatabaseHandler.TeamTable} WHERE ${DatabaseHandler.TeamEventID} = $eventID"

        try
        {
            var cursor = db!!.rawQuery(selectQuery, null)

            if (cursor != null && cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.TeampkID))
                    val name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TeamName))
                    val score = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.TeamScore))
                    arrayList.add(Team(id, name, score))
                }
                while (cursor.moveToNext())
            }
            cursor.close()
        }
        catch (e: SQLiteException)
        {
            // if cursor has a sql exception
            Log.e(object{}.javaClass.enclosingMethod.name, e.message)
            return null!!
        }
        return arrayList
    }

    /**
     * Read all the player IDs for a individual team
     * @param teamID : team identifier
     * @return an ArrayList or null
     */
    fun ReadPlayerIDsForTeamID(teamID : Int) : ArrayList<Int>
    {
        var db = _DB.GetReadableDataBaseObject()
        var arrayList = ArrayList<Int>()

        var selectQuery = "SELECT * FROM ${DatabaseHandler.TeamPlayerMappingTable} WHERE ${DatabaseHandler.TeamID} = $teamID"

        try
        {
            var cursor = db!!.rawQuery(selectQuery, null)

            if (cursor != null && cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerID))
                    arrayList.add(id)
                }
                while (cursor.moveToNext())
            }
            cursor.close()
        }
        catch (e: SQLiteException)
        {
            // if cursor has a sql exception
            Log.e(object{}.javaClass.enclosingMethod.name, e.message)
            return null!!
        }
        return arrayList
    }

    /**
     * Update the goals scored by a team
     * @param teamID : the id of the Team to update
     * @param score : the number of goals the team scored
     * @return the number of rows affected or 0
     */
    fun UpdateTeamScore(teamID : Int, score: Int) : Int
    {
        var db = _DB.GetWritableDataBaseObject()

        var result = 0

        var values = ContentValues()
        values.put(DatabaseHandler.TeamScore, score)

        result = db!!.update(DatabaseHandler.TeamTable, values, "${DatabaseHandler.TeampkID}=$teamID", null)

        return result
    }
}