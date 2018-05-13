package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.Utilities.UtilityMethods

class EventStatsActivity : AppCompatActivity()
{

    private var EventID = 0
    private lateinit var Event : GroupEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_stats)

        EventID = intent.getStringExtra("EventID").toInt()
        Event = RequestEventForEventID(EventID)

        InitialiseNumberPickers()

        var btn_update = findViewById<Button>(R.id.btnUpdateEvent)
        btn_update.setOnClickListener()
        {
            UpdateEvent(Event)
        }

        RequestAndDisplayEventStats(Event)
    }

    private fun InitialiseNumberPickers() {
        var nbrPickerTeamOne = findViewById<NumberPicker>(R.id.pickerTeamOne)
        //Set the minimum value of NumberPicker
        nbrPickerTeamOne.minValue = 0;
        //Specify the maximum value/number of NumberPicker
        nbrPickerTeamOne.maxValue = 10;

        var nbrPickerTeamTwo = findViewById<NumberPicker>(R.id.pickerTeamTwo)
        //Set the minimum value of NumberPicker
        nbrPickerTeamTwo.minValue = 0;
        //Specify the maximum value/number of NumberPicker
        nbrPickerTeamTwo.maxValue = 10
    }

    private fun RequestEventForEventID(eventID: Int) : GroupEvent
    {
        var eventManager = EventManager(this)
        var event = eventManager.GetEventByID(EventID)

        return event
    }

    /**
     * Request the relevant data for an event and display contents of screen
     * @param eventID : the unique ID of the event
     */
    private fun RequestAndDisplayEventStats(event: GroupEvent)
    {

        var txtEventDate = findViewById<TextView>(R.id.txtViewEventDateContent)
        txtEventDate.text = UtilityMethods.ConvertDateTimeToString(event.Date)

        var nbrPickerTeamOne = findViewById<NumberPicker>(R.id.pickerTeamOne)
        nbrPickerTeamOne.value = event.EventTeams[0].Score

        var nbrPickerTeamTwo = findViewById<NumberPicker>(R.id.pickerTeamTwo)
        nbrPickerTeamTwo.value = event.EventTeams[1].Score
    }

    private fun UpdateEvent(event: GroupEvent)
    {

    }

    /**
     * Handle the update of the score for the provided team
     * @param team : the team with the provided score
     */
    private fun UpdateTeamScore(team : Team)
    {

    }
}
