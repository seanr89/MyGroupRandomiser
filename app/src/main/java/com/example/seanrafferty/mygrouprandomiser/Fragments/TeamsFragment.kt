package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.Team

import com.example.seanrafferty.mygrouprandomiser.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TeamsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TeamsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TeamsFragment : androidx.fragment.app.Fragment()
{
    private var listener: OnFragmentInteractionListener? = null
    var _Teams : ArrayList<Team> = arrayListOf()

    //Recycler manager details
    private lateinit var teamOneviewManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    private lateinit var teamOnePlayerRecycler : androidx.recyclerview.widget.RecyclerView
    private lateinit var teamOnePlayerAdapter : PlayerRecyclerAdapter

    private lateinit var teamTwoviewManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    private lateinit var teamTwoPlayerRecycler : androidx.recyclerview.widget.RecyclerView
    private lateinit var teamTwoPlayerAdapter : PlayerRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //this is how i should have setup credentials for the two teams!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_team, container, false)

        val bundle = arguments

        _Teams = bundle!!.getSerializable(ARG_TEAMS) as ArrayList<Team>

        if(_Teams != null && _Teams.isNotEmpty())
        {
            InitialiseTeamOneRecycler(_Teams[0], view)
            InitialiseTeamTwoRecycler(_Teams[1], view)
        }
        else
        {
            InitialiseTeamOneRecycler(Team(0, "Team One", 0), view)
            InitialiseTeamTwoRecycler(Team(0, "Team Two", 0), view)
        }

        var btn_save_event = view.findViewById<Button>(R.id.btn_save_event)
        btn_save_event.setOnClickListener()
        {
            listener!!.onFragmentInteraction(_Teams)
        }

        return view
    }

    private fun InitialiseTeamOneRecycler(team: Team, view:View)
    {
        Log.d("TeamsFragment", object{}.javaClass.enclosingMethod.name)
        teamOneviewManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        teamOnePlayerAdapter = PlayerRecyclerAdapter(team.Players, false)
        teamOnePlayerRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.TeamOnePlayerRecycler).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = teamOneviewManager

            // specify an viewAdapter (see also next example)
            adapter = teamOnePlayerAdapter
        }
    }

    private fun InitialiseTeamTwoRecycler(team: Team, view:View)
    {
        Log.d("TeamsFragment", object{}.javaClass.enclosingMethod.name)
        teamTwoviewManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        teamTwoPlayerAdapter = PlayerRecyclerAdapter(team.Players, false)
        teamTwoPlayerRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.TeamTwoPlayerRecycler).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = teamTwoviewManager

            // specify an viewAdapter (see also next example)
            adapter = teamTwoPlayerAdapter
        }
    }

    /**
     * Ah this could be the to handle any and all calls
     */
    override fun onAttach(context: Context)
    {
        //Log.d("TeamsFragment", object{}.javaClass.enclosingMethod.name + " number: " + ID)
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Handle the updating of the currently selected teams and refresh the two recycler adapters
     * @param teams : list of two team objects
     */
    fun UpdateRecyclerAdapter(teams: ArrayList<Team>)
    {
        Log.d("TeamsFragment", object{}.javaClass.enclosingMethod.name)
        _Teams = teams
        teamOnePlayerAdapter.playerList = teams[0].Players
        teamOnePlayerAdapter.notifyDataSetChanged()

        teamTwoPlayerAdapter.playerList = teams[1].Players
        teamTwoPlayerAdapter.notifyDataSetChanged()
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
        fun onFragmentInteraction(teams : ArrayList<Team>)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_TEAM_NUMBER = "section_number"
        private val ARG_TAG = "tag_name"
        private val ARG_TEAMS = "teams"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param teams Parameter 1.
         * @param number Parameter 2.
         * @return A new instance of fragment TeamsFragment.
         */
        @JvmStatic
        fun newInstance(teams : ArrayList<Team>, number: Int, tag:String): TeamsFragment {
            val fragment = TeamsFragment()
            val args = Bundle()
            args.putSerializable(ARG_TEAMS, teams)
            args.putInt(ARG_TEAM_NUMBER, number)
            args.putString(ARG_TAG, tag)
            fragment.arguments = args
            return fragment
        }
    }
}
