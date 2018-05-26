package com.example.seanrafferty.mygrouprandomiser.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.Team

import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.Utilities.SelectionOption


/**
 * A simple [Fragment] subclass.
 * Use the [SingleTeamFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SingleTeamFragment : Fragment() {

    //initialise the team and player recycler object
    private lateinit var team : Team
    private lateinit var playerRecyclerAdapter: PlayerRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            team = it.getSerializable(ARG_TEAM) as Team
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_single_team, container, false)

        //check that there is players available
        if(team.Players.isNotEmpty())
        {
            val teamviewManager = LinearLayoutManager(activity)
            playerRecyclerAdapter = PlayerRecyclerAdapter(team.Players, false, SelectionOption.NO_SELECT)
            view.findViewById<RecyclerView>(R.id.recyclerViewTeamPlayers).apply{
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)
                // use a linear layout manager
                layoutManager = teamviewManager
                // specify an viewAdapter (see also next example)
                adapter = playerRecyclerAdapter
            }
        }
        else
        {
            Toast.makeText(activity, "No Players for the team", Toast.LENGTH_LONG).show()
        }
        return view
    }

    /*
    Companion static class for fragment
     */
    companion object {

        private val ARG_TEAM = "TEAM_ARG"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param team : team parameter with player data
         * @return A new instance of fragment SingleTeamFragment.
         */
        @JvmStatic
        fun newInstance(team : Team) =
                SingleTeamFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_TEAM, team)
                    }
                }
    }
}
