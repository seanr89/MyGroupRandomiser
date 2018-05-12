package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager

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
     * 
     */
    private fun UpdateTeamScore()
    {

    }
}
