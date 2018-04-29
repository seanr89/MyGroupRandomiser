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
import com.example.seanrafferty.mygrouprandomiser.Business.TeamRandomiser
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.MyGroupDBHandler
import java.text.SimpleDateFormat
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
class EventSetupFragment : Fragment()
{
    private var mCallback: OnRandomTeamsGenerated? = null

    //Recycler manager details
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var _PlayerRecycler : RecyclerView
    private lateinit var _PlayerAdapter : PlayerRecyclerAdapter
    //var GroupID : Int = 0

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

        val viewDate: TextView = view.findViewById(R.id.EventDateView)
        val viewTime: TextView = view.findViewById(R.id.EventTimeView)

        var playerList = MyGroupDBHandler(DatabaseHandler(context)).ReadAllPlayersForAGroup(MyGroup(arguments!!.getInt(ARG_GROUP_NUMBER), ""))
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
            CreateRandomTeams()
        }

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
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }

    /**
     * Open a date dialog and handle the on set listener to update a textview
     */
    private fun SetDate(textView: TextView)
    {
        Log.d("EventSetupFragment", object{}.javaClass.enclosingMethod.name)

        val cal = Calendar.getInstance()

        val datePickerDialog : DatePickerDialog = DatePickerDialog(activity)
        datePickerDialog.show()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        datePickerDialog.setOnDateSetListener(dateSetListener)

        textView.text = SimpleDateFormat("dd.MM.yyyy").format(cal.time)
    }

    /**
     * Open a time dialog and handle the on set listender to update text view
     */
    private fun SetTime(textView: TextView, view : View)
    {
        Log.d("EventSetupFragment", object{}.javaClass.enclosingMethod.name)

        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialogListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
        }

        val dialog = TimePickerDialog(activity, timePickerDialogListener, hour, minute, true)
        dialog.show()
        textView.text = SimpleDateFormat("HH:mm").format(cal.time)
    }

    /**
     * Operation to trigger the generation of two teams with random player assignment
     */
    private fun CreateRandomTeams()
    {
        Log.d("EventSetupFragment", object{}.javaClass.enclosingMethod.name)

        var players = GetSelectedPlayers()
        //Log.d("Method", object{}.javaClass.enclosingMethod.name + " player count: ${players.size}")
        if(!players.isEmpty())
        {
            //Log.d(object{}.javaClass.enclosingMethod.name, "Player count : ${players.size}")
            var randomiser = TeamRandomiser()
            var teamArray = randomiser.RandomizePlayerListIntoTeams(players)

           // Log.d(object{}.javaClass.enclosingMethod.name, "team1 size: ${teamArray[0].Players.size}")
           // Log.d(object{}.javaClass.enclosingMethod.name, "team2 size: ${teamArray[1].Players.size}")

            //then return back to where it needs to go - ie the fragment activity
            mCallback!!.onTeamsRandomized(teamArray)
            return
        }
        Toast.makeText(context, "No Players Selected!", Toast.LENGTH_LONG).show()
    }

    // Container Activity must implement this interface
    interface OnRandomTeamsGenerated {
        fun onTeamsRandomized(teams : ArrayList<Team>)
    }

    /**
     *
     */
    fun GetSelectedPlayers() : ArrayList<Player>
    {
        Log.d("EventSetupFragment", object{}.javaClass.enclosingMethod.name)
        return _PlayerAdapter.SelectedItems
    }


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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TeamFragment.
         */
        // TODO: Rename and change types and number of parameters
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