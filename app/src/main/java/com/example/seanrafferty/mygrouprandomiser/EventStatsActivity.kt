package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Models.Team

class EventStatsActivity : AppCompatActivity()
{

    private var EventID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_stats)

        EventID = intent.getStringExtra("EventID").toInt()

        RequestAndDisplayEventStats(EventID)
    }

    /**
     * Request the relevant data for an event and display contents of screen
     * @param eventID : the unique ID of the event
     */
    private fun RequestAndDisplayEventStats(eventID: Int)
    {
        var eventManager = EventManager(this)
        var event = eventManager.GetEventByID(EventID)
    }

    /**
     * Handle the update of the score for the provided team
     * @param team : the team with the provided score
     */
    private fun UpdateTeamScore(team : Team)
    {

    }
}
