package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
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
            // exception on the query fall over
            Log.e("EXCEPTION", "query failed with message : ${e.message}")
            return null!!
        }

        //check the cursor has an item then start processing using a while loop
        if (cursor!!.moveToFirst()) {
            do
            {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventpkID))
                val date = UtilityMethods.ConvertISODateStringToDateTime(cursor.getString(cursor.getColumnIndex(DatabaseHandler.EventDate)))
                val complete = UtilityMethods.ConvertIntToBoolean(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventCompleted)))
                val groupID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventGroupID))

                arrayList.add(GroupEvent(id, date, groupID, complete))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return arrayList
    }

    /**
     * Read a single group event by the ID
     * @param id : unique group event ID
     * @return the populated group event
     */
    fun GetEventByID(id:Int) : GroupEvent
    {
        var selectQuery = "SELECT * FROM ${DatabaseHandler.EventTable} WHERE ${DatabaseHandler.EventpkID} = ${id}"
        val db = _DB.GetReadableDataBaseObject()

        var cursor: Cursor? = null
        try {
            cursor = db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e("EXCEPTION", "query failed with message : ${e.message}")
            return null!!
        }

        if (cursor!!.moveToFirst()) {
            do
            {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventpkID))
                val date = UtilityMethods.ConvertISODateStringToDateTime(cursor.getString(cursor.getColumnIndex(DatabaseHandler.EventDate)))
                val complete = UtilityMethods.ConvertIntToBoolean(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventCompleted)))
                val groupID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.EventGroupID))

                return GroupEvent(id, date, groupID, complete)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return null!!
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
            if (mappedGroups != null || !mappedGroups.isEmpty())
            {
                resultList = mappedGroups as ArrayList<GroupEvent>
            }
        }
        return resultList
    }

    /**
     * Query and return the count of events for a group
     * @return a integer count of events
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

    /**
     * Insert an new group event
     * @param event : group event to be parsed and inserted
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun CreateGroupEvent(event:GroupEvent) :Int
    {
        Log.d("EventDBHandler", object{}.javaClass.enclosingMethod.name)

        var db = _DB.GetWritableDataBaseObject()

        var result : Int

        var values = ContentValues()
        values.put(DatabaseHandler.EventDate, UtilityMethods.ConvertDateToSQLString(event.Date))
        values.put(DatabaseHandler.EventGroupID, event.GroupID)
        //default the event to incomplete
        values.put(DatabaseHandler.EventCompleted, 0)

        result = db!!.insert(DatabaseHandler.EventTable, "", values).toInt()

        return result
    }

    /**
     * Update an event to be completed
     * @param event : the event with its respective ID
     * @param completed : integer parameter (default = 0) to identify if the event is completed
     * @return the number of rows affected or 0 if failed
     */
    fun UpdateEventCompleted(event: GroupEvent, completed : Int = 0) : Int
    {
        Log.d("EventDBHandler", object{}.javaClass.enclosingMethod.name)

        var db = _DB.GetWritableDataBaseObject()
        var result = 0

        var values = ContentValues()
        //1 de-notes completed
        values.put(DatabaseHandler.EventCompleted, completed)

        result = db!!.update(DatabaseHandler.EventTable, values, "${DatabaseHandler.EventpkID}=${event.ID}", null)

        return result
    }
}