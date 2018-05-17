package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
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
     * @param groupEvent : populated group event with 2 teams
     */
    fun SaveEvent(groupEvent: GroupEvent)
    {
        Log.d("EventManager", object{}.javaClass.enclosingMethod.name)

        //Initialise the two database contexts to save the event and teams
        var eventDB = EventDBHandler(DatabaseHandler(context))
        var teamDB = TeamDBHandler(DatabaseHandler(context))

        var eventID = eventDB.CreateGroupEvent(groupEvent)
        groupEvent.ID = eventID

        //SEAN- CONVERT TO JSON MULTI SAVE

        //Create the team and insert player team mappings for team one
        var teamOneID = teamDB.InsertTeam(groupEvent.EventTeams[0], groupEvent.ID)
        for(teamOnePlayer : Player in groupEvent.EventTeams[0].Players)
        {
            teamDB.CreateTeamPlayerMapping(teamOneID, teamOnePlayer.ID)
        }

        //Create the team and insert player team mappings for team two
        var teamTwoID = teamDB.InsertTeam(groupEvent.EventTeams[1], groupEvent.ID)
        for(teamTwoPlayer : Player in groupEvent.EventTeams[1].Players)
        {
            teamDB.CreateTeamPlayerMapping(teamTwoID, teamTwoPlayer.ID)
        }
    }

    /**
     * Handle the removal of all events marked as completed from the provided events
     * @param events : the list of events
     * @return a filtered collection of events
     */
    fun RemoveCompletedEventsFromList(events: ArrayList<GroupEvent>) : ArrayList<GroupEvent>
    {
        return events.filter { it.Completed == true } as ArrayList<GroupEvent>
    }

    /**
     * Not Yet Implemented
     * Request a full event by ID : with teams and players included!
     */
    fun GetEventByID(eventID: Int) : GroupEvent
    {
        var DBHandler = DatabaseHandler(context)
        var event = EventDBHandler(DBHandler).GetEventByID(eventID)

        var teamsManager = TeamManager(context)
        event.EventTeams = teamsManager.ReadTeamsForEvent(eventID)

        return event
    }

    /**
     * Request all events for a group - does not yet support team and player details
     * @param myGroup : group to request events
     * @return an arraylist of group events
     */
    fun GetAllEventsForAGroup(myGroup: MyGroup) : ArrayList<GroupEvent>
    {
        Log.d("EventManager", object{}.javaClass.enclosingMethod.name)

        var db = EventDBHandler(DatabaseHandler(context))
        var events = db.GetAllGroupEventsForGroup(myGroup)

        return events
    }

    /**
     * Update an event to be completed (only that so far)
     * @param groupEvent : the event to be updated to be completed
     */
    fun EventComplete(groupEvent: GroupEvent)
    {
        Log.d("EventManager", object{}.javaClass.enclosingMethod.name)

        var eventDB = EventDBHandler(DatabaseHandler(context))
        eventDB.UpdateEventCompleted(groupEvent, 1)
    }

    /**
     * Update an event status to be incomplete
     * @param groupEvent : the event to be updated to be incomplete
     */
    fun EventInComplete(groupEvent: GroupEvent)
    {
        Log.d("EventManager", object{}.javaClass.enclosingMethod.name)

        var eventDB = EventDBHandler(DatabaseHandler(context))
        eventDB.UpdateEventCompleted(groupEvent, 0)
    }
}