package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
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
     * @return : Int to denote the success status
     */
    fun SaveEvent(groupEvent: GroupEvent)
    {
        //Initialise the two database contexts to save the event and teams
        var EventDB = EventDBHandler(DatabaseHandler(context))
        var TeamDB = TeamDBHandler(DatabaseHandler(context))

        var EventID = EventDB.CreateGroupEvent(groupEvent)
        groupEvent.ID = EventID

        //Create the team and insert player team mappings for team one
        var teamOneID = TeamDB.CreateTeam(groupEvent.EventTeams[0], groupEvent.ID)
        for(teamOnePlayer : Player in groupEvent.EventTeams[0].Players)
        {
            TeamDB.CreateTeamPlayerMapping(teamOneID, teamOnePlayer.ID)
        }

        //Create the team and insert player team mappings for team two
        var teamTwoID = TeamDB.CreateTeam(groupEvent.EventTeams[2], groupEvent.ID)
        for(teamTwoPlayer : Player in groupEvent.EventTeams[1].Players)
        {
            TeamDB.CreateTeamPlayerMapping(teamTwoID, teamTwoPlayer.ID)
        }
    }
}