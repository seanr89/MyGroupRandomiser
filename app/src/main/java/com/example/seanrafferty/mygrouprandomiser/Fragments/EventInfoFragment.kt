package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Business.TeamManager
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.Utilities.UtilityMethods

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventInfoFragment : androidx.fragment.app.Fragment()  {

    private lateinit var event: GroupEvent
    private val TAG =  "EventInfoFragment"
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            event = it.getSerializable(ARG_EVENT) as GroupEvent
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragment = inflater.inflate(R.layout.fragment_event_info, null)

        var txtViewDate = fragment.findViewById<TextView>(R.id.txtViewEventDateContent)
        txtViewDate.text = UtilityMethods.ConvertDateTimeToString(event.Date)

        InitialiseNumberPickers(event, fragment)

        SetCompletedSwitch(event, fragment)
        SetBalancedSwitchForEvent(event, fragment)

        //setup the button event for updating event details
        var btn_update = fragment.findViewById<Button>(R.id.btnUpdateEvent)
        btn_update.setOnClickListener()
        {
            Toast.makeText(context, "Button Disabled", Toast.LENGTH_LONG).show()
        }

        //Move to the completed switch to handle event update!!
        var switch = fragment.findViewById<Switch>(R.id.switchEventCompleted)
        switch.setOnClickListener()
        {
            UpdateEvent(event, fragment)
            Toast.makeText(context, "Event Saved!", Toast.LENGTH_LONG).show()
        }

        return fragment
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    /**
     * Handle detachment of the fragment
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Handle initialisation of default values for team pickers
     * @param event :
     * @param fragmentView :
     */
    private fun InitialiseNumberPickers(event: GroupEvent, fragmentView : View)
    {
        var nbrPickerTeamOne = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamOne)
        //Set the minimum value of NumberPicker
        nbrPickerTeamOne.minValue = 0
        //Specify the maximum value/number of NumberPicker and set the score
        nbrPickerTeamOne.maxValue = 15
        nbrPickerTeamOne.value = event.EventTeams[0].Score

        var nbrPickerTeamTwo = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamTwo)
        //Set the minimum value of NumberPicker
        nbrPickerTeamTwo.minValue = 0
        //Specify the maximum value/number of NumberPicker and set the team two score
        nbrPickerTeamTwo.maxValue = 15
        nbrPickerTeamTwo.value = event.EventTeams[1].Score
    }

    /**
     * Set the switch to be enabled or disabled based on the event status
     * @param event : event to read the event status
     * @param fragmentView : the current fragment view
     */
    private fun SetCompletedSwitch(event:GroupEvent, fragmentView: View)
    {
        var switch = fragmentView.findViewById<Switch>(R.id.switchEventCompleted)
        switch.isChecked = event.Completed
    }

    /**
     * Set the switch to be enabled or disabled based on the event balanced rating
     * @param event : event to read the event status
     * @param fragmentView : the current fragment view
     */
    private fun SetBalancedSwitchForEvent(event:GroupEvent, fragmentView: View)
    {
        var switch = fragmentView.findViewById<Switch>(R.id.switchEventBalanced)
        switch.isChecked = event.Balanced
    }

    /**
     * Handle the updating of event team scores and completed status
     * @param event : the GroupEvent
     * @param fragmentView :
     */
    private fun UpdateEvent(event: GroupEvent, fragmentView: View)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val eventManager = EventManager(context)

        //Request current checked status of the switch and update the completion status accordingly
        var switch = fragmentView.findViewById<Switch>(R.id.switchEventCompleted)
        if(!switch.isChecked)
        {
            //eventManager.EventComplete(event)
            Toast.makeText(context, "Event is already complete!", Toast.LENGTH_LONG).show()
            return
        }

        //Team1
        var nbrPickerTeamOne = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamOne)
        event.EventTeams[0].Score = nbrPickerTeamOne.value
        //Team2
        var nbrPickerTeamTwo = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamTwo)
        event.EventTeams[1].Score = nbrPickerTeamTwo.value
        //Balanced
        val balancedSwitch = fragmentView.findViewById<Switch>(R.id.switchEventBalanced)
        event.Balanced = balancedSwitch.isChecked

        eventManager.UpdateEventOnCompletion(event)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_EVENT = "EVENT_ARG"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param event the event parameter
         * @return A new instance of fragment EventInfoFragment.
         */
        @JvmStatic
        fun newInstance(event : GroupEvent) =
                EventInfoFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(EventInfoFragment.ARG_EVENT, event)
                    }
                }
    }
}
