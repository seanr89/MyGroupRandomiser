package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Switch
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Business.TeamManager
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

        //setup the button event for updating event details
        var btn_update = findViewById<Button>(R.id.btnUpdateEvent)
        btn_update.setOnClickListener()
        {
            UpdateEvent(Event)
        }

        RequestAndDisplayEventStats(Event)
    }

    /**
     * Handle initialisation of default values for team pickers
     */
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


    /**
     * Request the event and its accompanying data
     * @param eventID : the unique ID for a single event
     * @return a populated GroupEvent object
     */
    private fun RequestEventForEventID(eventID: Int) : GroupEvent
    {
        var eventManager = EventManager(this)

        return eventManager.GetEventByID(EventID)
    }

    /**
     * Request the relevant data for an event and display contents of screen
     * @param event : the event object to parse event and team score data
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
        var nbrPickerTeamOne = findViewById<NumberPicker>(R.id.pickerTeamOne)
        event.EventTeams[0].Score = nbrPickerTeamOne.value
        UpdateTeamScore(nbrPickerTeamOne.value, event.EventTeams[0])

        var nbrPickerTeamTwo = findViewById<NumberPicker>(R.id.pickerTeamTwo)
        event.EventTeams[1].Score = nbrPickerTeamTwo.value
        UpdateTeamScore(nbrPickerTeamTwo.value, event.EventTeams[1])

        val eventManager = EventManager(this)

        //Request current checked status of the switch and update the completion status accordingly
        var switch = findViewById<Switch>(R.id.switchEventCompleted)
        if(switch.isChecked)
        {
            eventManager.EventComplete(Event)
        }
        else
        {
            eventManager.EventInComplete(Event)
        }
    }

    /**
     * Handle the update of the score for the provided team
     * @param team : the team with the provided score
     */
    private fun UpdateTeamScore(score: Int, team : Team)
    {
        var teamManager = TeamManager(this)
        teamManager.UpdateTeamScore(score, team)
    }
}
