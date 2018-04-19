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
    fun ReadAllPlayersForAGroup(group:MyGroup) : MutableList<Player>
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
            var player = players.filter { it.ID == item } as Player
            if(player != null)
            {
                modelList.add(player)
            }
        }
        return modelList
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
}