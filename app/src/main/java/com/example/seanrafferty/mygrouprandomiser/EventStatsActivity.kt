package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.Utilities.UtilityMethods

class EventStatsActivity : AppCompatActivity()
{

    private var EventID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_stats)

        EventID = intent.getStringExtra("EventID").toInt()

        var nbrPickerTeamOne = findViewById<NumberPicker>(R.id.pickerTeamOne)
        //Set the minimum value of NumberPicker
        nbrPickerTeamOne.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        nbrPickerTeamOne.setMaxValue(10);

        var nbrPickerTeamTwo = findViewById<NumberPicker>(R.id.pickerTeamTwo)
        //Set the minimum value of NumberPicker
        nbrPickerTeamTwo.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        nbrPickerTeamTwo.setMaxValue(10);

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

        var txtEventDate = findViewById<TextView>(R.id.txtViewEventDateContent)
        txtEventDate.text = UtilityMethods.ConvertDateTimeToString(event.Date)

        var nbrPickerTeamOne = findViewById<NumberPicker>(R.id.pickerTeamOne)
        nbrPickerTeamOne.value = event.EventTeams[0].Score

        var nbrPickerTeamTwo = findViewById<NumberPicker>(R.id.pickerTeamTwo)
        nbrPickerTeamTwo.value = event.EventTeams[1].Score
    }

    /**
     * Handle the update of the score for the provided team
     * @param team : the team with the provided score
     */
    private fun UpdateTeamScore(team : Team)
    {

    }
}
