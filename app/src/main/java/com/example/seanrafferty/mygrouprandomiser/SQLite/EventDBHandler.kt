package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Utilities.UtilityMethods

/**
 * DB Handler for Event data
 */
class EventDBHandler
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
     * Read all stored Events without there respective team list
     * @return : a list of GroupEvents
     */
    fun GetAllEvents() : ArrayList<GroupEvent>
    {
        Log.d("EventDBHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList = ArrayList<GroupEvent>()

        var selectQuery = "SELECT * FROM ${DatabaseHandler.EventTable}"
        val db = _DB.GetReadableDataBaseObject()

        var cursor: Cursor? = null
        try {
            cursor = db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            return null!!
        }

        if (cursor!!.moveToFirst()) {
            do
            {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventpkID))
                val date = UtilityMethods.Companion.ConvertStringToDateTime(cursor.getString(cursor.getColumnIndex(DatabaseHandler.EventDate)))
                val groupID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventGroupID))

                arrayList.add(GroupEvent(id, date, groupID))
            } while (cursor.moveToNext())
        }

        return arrayList
    }

    /**
     * Query all the events for a group - Note without Teams list
     * @param group : the MyGroup object to query
     * @return : A collection of events for a group filtered by ID
     */
    fun GetAllGroupEventsForGroup(group:MyGroup) : ArrayList<GroupEvent>
    {
        Log.d("EventDBHandler", object{}.javaClass.enclosingMethod.name)

        var allGroupEvents = GetAllEvents()
        var resultList = ArrayList<GroupEvent>()

        //if any group events where found
        if(!allGroupEvents.isEmpty())
        {
            //now to filter - check if the current player is already assigned to the group!!
            var mappedGroups = allGroupEvents.filter { it.GroupID == group.ID }
            if (mappedGroups == null || mappedGroups.isEmpty())
            {
                resultList = mappedGroups as ArrayList<GroupEvent>
            }
        }
        return resultList
    }

    /**
     * Query and return the count of events for a group
     */
    fun GetCountOfEventsForGrouo(group : MyGroup) : Int
    {
        var resultList = GetAllGroupEventsForGroup(group)
        var result = 0

        if(!resultList.isEmpty())
        {
            result = resultList.size
        }
        return result
    }

    fun CreateGroupEvent(event:GroupEvent) :Int
    {
        Log.d("EventDBHandler", object{}.javaClass.enclosingMethod.name)

        return 0
    }
}