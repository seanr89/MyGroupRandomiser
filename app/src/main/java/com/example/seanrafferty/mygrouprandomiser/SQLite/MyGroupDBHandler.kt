package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player

class MyGroupDBHandler
{
    lateinit var _DB : DatabaseHandler

    /**
     * constructor for player DB handler
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    /**
     * Read all the players for a provided group
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
     *
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
        return arrayList;
    }
}