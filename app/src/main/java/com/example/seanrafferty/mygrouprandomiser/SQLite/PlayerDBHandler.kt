package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.Mappings.PlayerSkillPlayerMapping
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.Utilities.UtilityMethods

/**
Database Handler relating solely to Player info
 */
class PlayerDBHandler
{
    var _DB : DatabaseHandler

    /**
     * constructor for player DB handler
     * @param db : DatabaseHandler to allow interaction
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    /**
     * Read all stored players on the system
     * @return ArrayList of players
     */
    fun ReadAllPlayers() : ArrayList<Player>
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        // Select All Query
        var selectQuery = "SELECT * FROM ${DatabaseHandler.PlayerTable}"
        val db = _DB.readableDatabase

        var cursor: Cursor? = null
        cursor = try {
            db!!.rawQuery(selectQuery, null)
        }
        catch (e: SQLiteException) {
            // exception on the query fall over
            Log.e("EXCEPTION", "query failed with message : ${e.message}")
            cursor!!.close()
            return null!!
        }

        var arrayList = ArrayList<Player>()
        if (cursor.moveToFirst())
        {
            do
            {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerpkID))
                val name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PlayerName))
                val rating = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.PlayerRating))
                arrayList.add(Player(id, name, rating))
            }
            while (cursor.moveToNext())

        }
        cursor.close()
        return arrayList
    }

    /**
     * Read all available players from the list of player IDs
     * @param playerIDs : the list of playerIDs to read player info for
     * @return a list of players
     */
    fun ReadAllPlayersInListOfIDs(playerIDs : ArrayList<Int>) : ArrayList<Player>
    {
        var arrayList = ArrayList<Player>()

        //combine all of the player IDs from the list to a comma separated string
        var combinedString = playerIDs.joinToString(",", "", "")

        // Select All Query
        var selectQuery = "SELECT * FROM ${DatabaseHandler.PlayerTable} WHERE ${DatabaseHandler.PlayerpkID} IN ($combinedString)"
        val db = _DB.readableDatabase

        var cursor: Cursor? = null
        cursor = try
        {
            db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            cursor!!.close()
            return null!!
        }

        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerpkID))
                    val name =cursor.getString(cursor.getColumnIndex(DatabaseHandler.PlayerName))
                    val rating = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.PlayerRating))
                    arrayList.add(Player(id, name, rating))
                }
                while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList
    }

    /**
     * Insert and Assign player list to a group
     * @param playerList : the list of players to assign to a group
     * @param group : the group to assign these players
     *
     */
    fun AssignPlayersToGroup(playerList: MutableList<Player>, group:MyGroup)
    {
        Log.d("PlayerDBHandler", object{}.javaClass.enclosingMethod.name)

        //Ensure database handler is writable
        var db = _DB.GetWritableDataBaseObject()

        for(item: Player in playerList)
        {
            var values = ContentValues()
            values.put(DatabaseHandler.PlayerID, item.ID)
            values.put(DatabaseHandler.GroupID, group.ID)

            db!!.insert(DatabaseHandler.GroupPlayerTable, "", values).toInt()
        }
    }

    /**
     * Insert a new player
     * @param player - Player object to be inserted
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun InsertPlayer(player: Player) : Int
    {
        Log.d("PlayerDBHandler", object{}.javaClass.enclosingMethod.name)

        var values = ContentValues()
        values.put(DatabaseHandler.PlayerName, player.Name)
        values.put(DatabaseHandler.PlayerRating, player.Rating)

        val db = _DB.GetWritableDataBaseObject()

        return try {
            db!!.insert(DatabaseHandler.PlayerTable, "", values).toInt()
        }
        catch (e: SQLiteException)
        {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            -1
        }
    }

    /**
     * Access and Update the rating of an individual player
     * @param player :
     * @return the id of the row affected or 0 if failed
     */
    fun UpdatePlayerRating(player: Player) : Int
    {
        var db = _DB.GetWritableDataBaseObject()

        var values = ContentValues()
        values.put(DatabaseHandler.PlayerRating, player.Rating)

        return try {
            db!!.update(DatabaseHandler.PlayerTable, values, "${DatabaseHandler.PlayerpkID}=${player.ID}", null)
        }
        catch(e : SQLiteException)
        {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            0
        }
    }


    ///////// PLAYER SKILL MAPPINGS /////////
    ///////// PLAYER SKILL MAPPINGS /////////

    fun ReadAllPlayerSkillPlayerMappings() : ArrayList<PlayerSkillPlayerMapping>
    {
        var arrayList = ArrayList<PlayerSkillPlayerMapping>()

        // Select All Query
        var selectQuery = "SELECT * FROM ${DatabaseHandler.PlayerSkillMappingTable}"
        val db = _DB.readableDatabase

        var cursor: Cursor? = null
        cursor = try
        {
            db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            cursor!!.close()
            return null!!
        }

        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val playerID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerSkillMappingPlayerID))
                    val skillID =cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerSkillMappingSkillID))
                    arrayList.add(PlayerSkillPlayerMapping(playerID, skillID))
                }
                while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList
        return null!!
    }

    /**
     * Attempt to insert the a mapping between a player skill and a player
     * @param player : the player with the id to insert
     * @param skill : the skill with a unique id to insert
     * r@eturn the id of the row inserted or -1 if failed
     */
    fun InsertPlayerSkillToPlayer(player: Player, skill: PlayerSkill) : Int
    {
        var values = ContentValues()
        values.put(DatabaseHandler.PlayerSkillMappingPlayerID, player.ID)
        values.put(DatabaseHandler.PlayerSkillMappingSkillID, skill.id)

        val db = _DB.GetWritableDataBaseObject()
        return try {
            db!!.insert(DatabaseHandler.PlayerSkillMappingTable, "", values).toInt()
        }
        catch (e: SQLiteException)
        {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            -1
        }
    }

    /**
     * Handle the deletion of all skills mappings for a player
     * @param player : the player with the respective id to remove the skills
     * @return the id of the row deleted or -1 if failed
     */
    fun DeletePlayerSkillMappingsForPlayer(player: Player) : Int
    {
        return null!!
    }
}