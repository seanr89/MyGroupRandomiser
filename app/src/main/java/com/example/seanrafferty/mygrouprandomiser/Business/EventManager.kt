package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.*
import com.example.seanrafferty.mygrouprandomiser.Models.enums.TeamStatus
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.EventDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.TeamDBHandler

/**
 * Business logic object to handle the managing of Group Events
 */
class EventManager(val context: Context?)
{
    private val TAG = "EventManager"
    /**
     * Operation to save the provided event (this is a for a newly created event)
     * @param groupEvent : populated group event with 2 teams
     */
    fun SaveEvent(groupEvent: GroupEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

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
        //Log.d("EventManager", object{}.javaClass.enclosingMethod.name)

        var db = EventDBHandler(DatabaseHandler(context))
        return db.GetAllGroupEventsForGroup(myGroup)
    }

    /**
     * Operation to query all Un-balanced events for a group
     * @param myGroup : the group to query
     * @return a filter array list of group events
     */
    fun GetAllUnBalancedEventsForGroup(myGroup: MyGroup) : ArrayList<GroupEvent>
    {
        var events = GetAllEventsForAGroup(myGroup) as List<GroupEvent>

        events = events.filter { it.Balanced == false }

        return events as ArrayList<GroupEvent>
    }

    /**
     * Update the balanced rating of a single event
     * @param event : the event to be updated
     * @return the number of rows affected or 0 if failed
     */
    fun UpdateEventBalancedStatus(event: GroupEvent) : Int
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var db = EventDBHandler(DatabaseHandler(context))
        return db.UpdateEventBalanced(event)
    }

    /**
     * Update an event to be completed (only that so far)
     * @param groupEvent : the event to be updated to be completed
     */
    fun EventComplete(groupEvent: GroupEvent)
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var eventDB = EventDBHandler(DatabaseHandler(context))
        eventDB.UpdateEventCompleted(groupEvent, 1)
    }

    /**
     * Operation to handle the editing of player ratings using team scores!
     * @param event : the team to process
     * @param teamStatus : if the team won, drew, lost or unknown
     *          - draw or unknown will just return atm
     * @param balanced : if the teams where rated as being balanced
     */
    fun UpdatePlayerRatingFromEventTeam(team: Team, teamStatus: TeamStatus = TeamStatus.UNKNOWN, balanced : Boolean = false)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        if(teamStatus == TeamStatus.DRAW || teamStatus == TeamStatus.UNKNOWN)
            return

        var playerManager = PlayerManager(context)
        for(item : Player in team.Players)
        {
            //Log.d(object{}.javaClass.enclosingMethod.name, "Updating player ${item.Name} from ${item.Rating}")
            item.Rating = updatePlayerRatingByBalanceAndStatus(item.Rating, teamStatus, balanced)
            //Log.d(object{}.javaClass.enclosingMethod.name, "Updating player ${item.Name} to ${item.Rating}")
            playerManager.UpdatePlayerRating(item)
        }
    }

    /**
     * Handle the updating of a players rating based on if they where in teh winning team and balanced status
     * @param rating : the rating value to update
     * @param teamStatus : the status of the team that the player is apart of
     * @param balanced : if the team was rated as balanced
     * @return the updated/re-calculated rating value
     */
    private fun updatePlayerRatingByBalanceAndStatus(rating : Double, teamStatus: TeamStatus, balanced: Boolean) : Double
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var newRating = rating
        when (teamStatus)
        {
            TeamStatus.WIN ->
            {
                newRating = if(!balanced) {
                    rating + 1.0
                } else {
                    rating + 2.0
                }
            }
            TeamStatus.LOSS ->
            {
                if(!balanced)
                {
                    newRating = rating - 1.0
                }
            }
            else ->
            {
                return newRating
            }
        }
        return newRating
    }

    /**
     * update the number of goals each team scored, the player ratings and if the teams where unbalanced on event completion
     * @param event : the event to update in the database when completed
     */
    fun UpdateEventOnCompletion(event: GroupEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //1. Update team scores
        var teamManager = TeamManager(context)
        teamManager.UpdateTeamScore(event.EventTeams[0].Score, event.EventTeams[0])
        teamManager.UpdateTeamScore(event.EventTeams[1].Score, event.EventTeams[1])

        //2. Update team balanced status
        UpdateEventBalancedStatus(event)

        //3. Update team player ratings!!
        UpdatePlayerRatingFromEventTeam(event.EventTeams[0], event.TeamOneStatus(), event.Balanced)
        UpdatePlayerRatingFromEventTeam(event.EventTeams[1], event.TeamTwoStatus(), event.Balanced)

        //4. Mark event as complete
        EventComplete(event)
    }
}