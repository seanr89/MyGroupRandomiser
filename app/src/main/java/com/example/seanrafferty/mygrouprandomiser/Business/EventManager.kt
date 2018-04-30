package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.EventDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.TeamDBHandler

/**
 * Business logic object to handle the managing of Group Events
 */
class EventManager(val context: Context)
{
    /**
     * Operation to save the provided event
     * @return : Int to denote the success status
     */
    fun SaveEvent(groupEvent: GroupEvent) : Int
    {
        var result = 0

        //Initialise the two database contexts to save the event and teams
        var EventDB = EventDBHandler(DatabaseHandler(context))
        var TeamDB = TeamDBHandler(DatabaseHandler(context))

        var EventID = EventDB.CreateGroupEvent(groupEvent)

        return result
    }
}