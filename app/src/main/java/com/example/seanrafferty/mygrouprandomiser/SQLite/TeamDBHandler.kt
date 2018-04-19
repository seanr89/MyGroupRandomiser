package com.example.seanrafferty.mygrouprandomiser.SQLite

import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class TeamDBHandler
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
     *
     */
    fun CreateTeam(team : Team)
    {

    }

    fun ReadTeamsForEvent(event:GroupEvent)
    {

    }
}