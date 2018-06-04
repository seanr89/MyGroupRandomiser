package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Switch
import android.widget.TextView
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
class EventInfoFragment : Fragment()  {

    private lateinit var event: GroupEvent
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            event = it.getSerializable(ARG_EVENT) as GroupEvent
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragment = inflater.inflate(R.layout.fragment_event_info, null);

        val date = UtilityMethods.ConvertDateTimeToString(event.Date)
        var txtViewDate = fragment.findViewById<TextView>(R.id.txtViewEventDateContent)
        txtViewDate.text = date

        InitialiseNumberPickers(event, fragment)

        //setup the button event for updating event details
        var btn_update = fragment.findViewById<Button>(R.id.btnUpdateEvent)
        btn_update.setOnClickListener()
        {
            UpdateEvent(event, fragment)
        }

        return fragment;
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
        Log.d("TAG ", object{}.javaClass.enclosingMethod.name)

        var nbrPickerTeamOne = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamOne)
        //Set the minimum value of NumberPicker
        nbrPickerTeamOne.minValue = 0
        //Specify the maximum value/number of NumberPicker
        nbrPickerTeamOne.maxValue = 10
        nbrPickerTeamOne.value = event.EventTeams[0].Score

        var nbrPickerTeamTwo = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamTwo)
        //Set the minimum value of NumberPicker
        nbrPickerTeamTwo.minValue = 0
        //Specify the maximum value/number of NumberPicker
        nbrPickerTeamTwo.maxValue = 10
        nbrPickerTeamTwo.value = event.EventTeams[1].Score
    }

    /**
     * Handle the updating of event team scores and completed status
     * @param event : the GroupEvent
     * @param fragmentView :
     */
    private fun UpdateEvent(event: GroupEvent, fragmentView: View)
    {
        Log.d("TAG ", object{}.javaClass.enclosingMethod.name)

        var nbrPickerTeamOne = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamOne)
        event.EventTeams[0].Score = nbrPickerTeamOne.value
        UpdateTeamScore(nbrPickerTeamOne.value, event.EventTeams[0])

        var nbrPickerTeamTwo = fragmentView.findViewById<NumberPicker>(R.id.pickerTeamTwo)
        event.EventTeams[1].Score = nbrPickerTeamTwo.value
        UpdateTeamScore(nbrPickerTeamTwo.value, event.EventTeams[1])

        val eventManager = EventManager(context)

        //Request current checked status of the switch and update the completion status accordingly
        var switch = fragmentView.findViewById<Switch>(R.id.switchEventCompleted)
        if(switch.isChecked)
        {
            eventManager.EventComplete(event)
        }
        else
        {
            eventManager.EventInComplete(event)
        }
    }

    /**
     * Handle the update of the score for the provided team
     * @param score : the score of the team
     * @param team : the team with the provided score
     */
    private fun UpdateTeamScore(score: Int, team : Team)
    {
        Log.d("TAG ", object{}.javaClass.enclosingMethod.name)

        var teamManager = TeamManager(context)
        teamManager.UpdateTeamScore(score, team)
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(event : GroupEvent) =
                EventInfoFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(EventInfoFragment.ARG_EVENT, event)
                    }
                }
    }
}
