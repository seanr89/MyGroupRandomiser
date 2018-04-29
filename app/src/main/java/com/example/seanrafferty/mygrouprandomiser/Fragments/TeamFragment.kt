package com.example.seanrafferty.mygrouprandomiser.Fragments

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
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.Team

import com.example.seanrafferty.mygrouprandomiser.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TeamFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TeamFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TeamFragment : Fragment()
{
    private var listener: OnFragmentInteractionListener? = null
    var ID : Int = 0

    //Recycler manager details
    private lateinit var teamOneviewManager: RecyclerView.LayoutManager
    private lateinit var teamOnePlayerRecycler : RecyclerView
    private lateinit var teamOnePlayerAdapter : PlayerRecyclerAdapter

    private lateinit var teamTwoviewManager: RecyclerView.LayoutManager
    private lateinit var teamTwoPlayerRecycler : RecyclerView
    private lateinit var teamTwoPlayerAdapter : PlayerRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_team, container, false)

        val bundle = arguments

        var teams = bundle!!.getSerializable(ARG_TEAMS) as ArrayList<Team>

        if(!teams.isEmpty())
        {
            InitialiseTeamOneRecycler(teams[0], view)
            InitialiseTeamTwoRecycler(teams[1], view)
        }
        else
        {
            InitialiseTeamOneRecycler(Team(0, "Team1"), view)
            InitialiseTeamTwoRecycler(Team(0, "Team2"), view)
        }

        return view
    }

    private fun InitialiseTeamOneRecycler(team: Team, view:View)
    {
        Log.d("TeamFragment", object{}.javaClass.enclosingMethod.name)
        teamOneviewManager = LinearLayoutManager(activity)
        teamOnePlayerAdapter = PlayerRecyclerAdapter(team.Players, true)
        teamOnePlayerRecycler = view.findViewById<RecyclerView>(R.id.TeamOnePlayerRecycler).apply{
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
        Log.d("TeamFragment", object{}.javaClass.enclosingMethod.name)
        teamTwoviewManager = LinearLayoutManager(activity)
        teamTwoPlayerAdapter = PlayerRecyclerAdapter(team.Players, true)
        teamTwoPlayerRecycler = view.findViewById<RecyclerView>(R.id.TeamTwoPlayerRecycler).apply{
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
        //Log.d("TeamFragment", object{}.javaClass.enclosingMethod.name + " number: " + ID)
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
//        _PlayerAdapter = PlayerRecyclerAdapter(arrayListOf(), true)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     *
     */
    fun UpdateRecyclerAdapter(teams: ArrayList<Team>)
    {
        Log.d("TeamFragment", object{}.javaClass.enclosingMethod.name)
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TeamFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(teams : ArrayList<Team>, number: Int, tag:String): TeamFragment {
            val fragment = TeamFragment()
            val args = Bundle()
            args.putSerializable(ARG_TEAMS, teams)
            args.putInt(ARG_TEAM_NUMBER, number)
            args.putString(ARG_TAG, tag)
            fragment.arguments = args
            return fragment
        }
    }
}
