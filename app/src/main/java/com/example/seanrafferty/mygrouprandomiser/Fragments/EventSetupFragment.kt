package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Business.TeamRandomiser
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.MyGroupDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.UtilityMethods
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventSetupFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventSetupFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventSetupFragment : Fragment(), ShuffleUpDialog.RandomisationSelectedListener
{
    private var mCallback: OnRandomTeamsGenerated? = null
    private var mCallbackSave : OnSaveEvent? = null

    //Recycler manager details
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var _PlayerRecycler : RecyclerView
    private lateinit var _PlayerAdapter : PlayerRecyclerAdapter

    private lateinit var viewDate : TextView
    private lateinit var viewTime : TextView

    private val TAG = "EventSetupFragment"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_event_setup, container, false)

        viewDate = view.findViewById(R.id.EventDateView)
        viewTime = view.findViewById(R.id.EventTimeView)

        var playerList = MyGroupManager(context).ReadAllPlayersForGroup(MyGroup(arguments!!.getInt(ARG_GROUP_NUMBER), ""))
        _PlayerAdapter = PlayerRecyclerAdapter(playerList, true)
        viewManager = LinearLayoutManager(activity)
        _PlayerRecycler = view.findViewById<RecyclerView>(R.id.PlayerEventRecycler).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = _PlayerAdapter
        }

        var btn_randomise = view.findViewById<Button>(R.id.btn_randomise_players)
        btn_randomise.setOnClickListener()
        {
            //CreateRandomTeams()
            //CreateTeamsByRatingAndTriggerEvent()
            ShuffleUpDialog.show(activity)
        }

        var btn_save_event = view.findViewById<Button>(R.id.btn_save_event)
        btn_save_event.setOnClickListener()
        {
            CreateEventAndAlertListener()
        }

        //Handle date and time event click events
        viewDate.setOnClickListener()
        {
            SetDate(viewDate)
        }
        viewTime.setOnClickListener()
        {
            SetTime(viewTime, view)
        }
        return view
    }

    /**
     * Ah this could be the to handle any and all calls
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventSetupFragment.OnRandomTeamsGenerated) {
            mCallback = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnRandomTeamsGenerated")
        }

        if(context is EventSetupFragment.OnSaveEvent) {
            mCallbackSave = context
        }
        else {
            throw RuntimeException(context.toString() + " must implement OnSaveEvent")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
        mCallbackSave = null
    }

    /**
     * Open a date dialog and handle the on set listener to update a textview
     */
    private fun SetDate(textView: TextView)
    {
        val cal = Calendar.getInstance()
        val datePickerDialog : DatePickerDialog = DatePickerDialog(activity)
        datePickerDialog.show()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            textView.text = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
        }
        datePickerDialog.setOnDateSetListener(dateSetListener)
    }

    /**
     * Open a time dialog and handle the on set listener to update text view
     */
    private fun SetTime(textView: TextView, view : View)
    {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialogListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        val dialog = TimePickerDialog(activity, timePickerDialogListener, hour, minute, true)
        dialog.show()
    }

    /**
     * Operation to trigger the generation of two teams with random player assignment
     */
    fun CreateRandomTeams()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var players = GetSelectedPlayers()
        if(players.isNotEmpty())
        {
            var randomiser = TeamRandomiser(context)
            var teamArray = randomiser.RandomizePlayerListIntoTeams(players)

            //then return back to where it needs to go - ie the fragment activity
            mCallback!!.onTeamsRandomized(teamArray)
            return
        }
        else
        {
            Toast.makeText(context, "No Players Selected!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Operation to trigger the shuffling of players and assignment of teams by rating!
     */
    fun CreateTeamsByRatingAndTriggerEvent()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var players = GetSelectedPlayers()
        if(players.isNotEmpty())
        {
            var randomiser = TeamRandomiser(context)
            var teamArray = randomiser.RandomizePlayerListBySortedRating(players)

            //then return back to where it needs to go - ie the fragment activity
            mCallback!!.onTeamsRandomized(teamArray)
            return
        }
        else
        {
            Toast.makeText(context, "No Players Selected!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * method to trigger the save event method for the accompanying activity listener
     */
    fun CreateEventAndAlertListener()
    {
        mCallbackSave!!.saveEvent()
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    // Container Activity must implement this interface
    interface OnRandomTeamsGenerated
    {
        fun onTeamsRandomized(teams : ArrayList<Team>)
    }

    /**
     * container activity must implement this interface to trigger the saving of the event
     */
    interface OnSaveEvent
    {
        fun saveEvent()
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    /**
     * N.B. no longer required as taken over by the parent activity
     * event listener call used to trigger the creation of team player lists randomly
     */
    override fun shufflePlayersRandomly() {
        //CreateRandomTeams()
    }

    /**
     * N.B. no longer required as taken over by the parent activity
     * event listener call used to trigger the creation of team player lists based on rating
     */
    override fun shufflePlayersByRating() {
        //CreateTeamsByRatingAndTriggerEvent()
    }

    /**
     * Request and return all selected players
     * @return all selected player items
     */
    fun GetSelectedPlayers() : ArrayList<Player>
    {
        return _PlayerAdapter.SelectedItems
    }

    /**
     * Handle the request for date and time string parameters and convert to LocalDateTime
     * @return ISO_Date_Time formatted localdatetime
     */
    fun GetSelectedDateAndTime() : LocalDateTime
    {
        //Log.d("TAG", object{}.javaClass.enclosingMethod.name)
        var Date = viewDate.text
        var Time = viewTime.text

        var dateTimeString = "$Date $Time"

        var dateTime : LocalDateTime

        dateTime = try {
            UtilityMethods.ConvertStringToDateTime(dateTimeString)
        }
        catch(e : DateTimeParseException) {
            // Throw invalid date message and spit back out the current date and time
            System.out.println("Exception was thrown when parsing date")
            LocalDateTime.now()
        }
        return dateTime
    }


    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_GROUP_NUMBER = "section_number"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param groupID : unique id of the group
         * @return A new instance of fragment EventSetupFragment.
         */
        @JvmStatic
        fun newInstance(groupID: Int): EventSetupFragment {
            val fragment = EventSetupFragment()
            val args = Bundle()
            args.putInt(ARG_GROUP_NUMBER, groupID)
            fragment.arguments = args
            return fragment
        }
    }
}
