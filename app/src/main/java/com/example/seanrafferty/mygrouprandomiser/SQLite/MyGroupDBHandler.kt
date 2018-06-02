package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player

class MyGroupDBHandler
{
    val _DB : DatabaseHandler

    /**
     * constructor for player DB handler
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    /**
     * INSERT a new Group to the database
     * @param group : the group to insert
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    @Throws(SQLiteConstraintException::class)
    fun CreateGroup(group: MyGroup) : Int
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        var result = 0

        var values = ContentValues()
        values.put("Name", group.Name)

        val db = _DB.GetWritableDataBaseObject()
        try
        {
            result = db!!.insertOrThrow(DatabaseHandler.groupTableName, "", values).toInt()
        }
        catch (e: SQLiteException)
        {
            Log.e("EXCEPTION", "Error on create ${e.message}")
        }
        return result;
    }

    /**
     * Read the information for an individual MyGroup object
     * @param id - a unique object for a MyGroup object
     * @return the group associated to the id - or null
     */
    @Throws(SQLiteConstraintException::class)
    fun ReadMyGroupByID(id:Int) : MyGroup
    {
        var selectQuery: String = "SELECT * FROM ${DatabaseHandler.groupTableName} WHERE ${DatabaseHandler.grouppkID} = $id"
        val db = _DB.GetReadableDataBaseObject()

        var group : MyGroup

        var cursor: Cursor? = null

        try {
            cursor = db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            return null!!
        }

        group = MyGroup()
        if (cursor!!.moveToFirst()) {
            group.ID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.grouppkID))
            group.Name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.groupName))
        }
        cursor.close()
        return group
    }

    /**
     * Read all the players for a provided group
     * @param group : the group to query
     * @return an list of players
     */
    fun ReadAllPlayersForAGroup(group:MyGroup) : ArrayList<Player>
    {
        Log.d("MyGroupDBHandler", object{}.javaClass.enclosingMethod.name)

        var modelList: MutableList<Player> = mutableListOf()

        //Get all of the player IDs
        var playerIDs = ReadAllPlayerIDsForGroup(group)

        var playerDBHandler = PlayerDBHandler(_DB)
        //Get all Players
        var players = playerDBHandler.ReadAllPlayers()

        for(item: Int in playerIDs)
        {
            var player = players.filter { it.ID == item }
            if(player != null)
            {
                modelList.add(player[0])
            }
        }
        return modelList as ArrayList<Player>
    }



    /**
     * Read all assigned player ids for a group
     * @param myGroup : the group to query for players assigned
     * @return a list of player ID's
     */
    fun ReadAllPlayerIDsForGroup(myGroup:MyGroup) : MutableList<Int>
    {
        Log.d("MyGroupDBHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList: MutableList<Int> = mutableListOf<Int>()
        // Select query with where in the clause
        var selectQuery: String = "SELECT * FROM ${DatabaseHandler.GroupPlayerTable} WHERE ${DatabaseHandler.GroupID} = ${myGroup.ID}"
        //Log.d("MyGroupDBHandler", "query $selectQuery")
        val db = _DB.GetReadableDataBaseObject()

        var cursor: Cursor?
        try
        {
            cursor = db!!.rawQuery(selectQuery, null)

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
     * Request the total number of players assigned a single group
     * @param group : the group to query
     * @return the count of players - default is zero
     */
    fun GetPlayerCountAssignedToGroup(group : MyGroup) : Int
    {
        var result = 0
        var resultList  = ReadAllPlayerIDsForGroup(group)
        if(!resultList.isEmpty())
        {
            result = resultList.size
        }
        return result
    }

    /**
     * Read and parse and Groups stored in the database
     * @return all available MyGroup objects
     */
    fun ReadAllGroups() : ArrayList<MyGroup>
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList = ArrayList<MyGroup>()

        // Select All Query
        var selectQuery = "SELECT * FROM ${DatabaseHandler.groupTableName}"
        val db = _DB.GetReadableDataBaseObject()

        var cursor = db!!.rawQuery(selectQuery, null)

        if(cursor != null)
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.grouppkID))
                    val name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.groupName))

                    arrayList.add(MyGroup(id, name))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList
    }
}