package com.example.seanrafferty.mygrouprandomiser.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
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
    lateinit var idTag : String
    private lateinit var playerRecycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d("TAG", object{}.javaClass.enclosingMethod.name)
        arguments?.let {
            team = it.getSerializable(ARG_TEAM) as Team
            idTag = it.getString(ARG_TAG)
        }
        playerRecyclerAdapter = PlayerRecyclerAdapter(arrayListOf(), false, SelectionOption.NO_SELECT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        retainInstance = true
        val view: View = inflater.inflate(R.layout.fragment_single_team, container, false)

        Log.d("TAG $idTag", object{}.javaClass.enclosingMethod.name)

        //check that there is players available
        if(team.Players != null && team.Players.isNotEmpty())
        {
            playerRecyclerAdapter.playerList = team.Players
        }
        else
        {
            playerRecyclerAdapter.playerList = arrayListOf()
        }

        val teamviewManager = LinearLayoutManager(activity)
        playerRecycler = view.findViewById<RecyclerView>(R.id.recyclerViewTeamPlayers).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = teamviewManager
            // specify an viewAdapter (see also next example)
            adapter = playerRecyclerAdapter
        }
        playerRecyclerAdapter.notifyDataSetChanged()

        return view
    }

    /**
     * Handle request to update player content
     * NEEDS TO BE PROPERLY DOCUMENTED AND TESTED
     */
    fun UpdateTeamPlayers(singleTeam : Team)
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        team = singleTeam
        try
        {
            if(playerRecyclerAdapter != null)
            {
                playerRecyclerAdapter.playerList = team.Players
                playerRecyclerAdapter.notifyDataSetChanged()
            }
        }
        catch(e : UninitializedPropertyAccessException)
        {
            Log.d("TAG", object{}.javaClass.enclosingMethod.name + "Exception caught")
            playerRecyclerAdapter = PlayerRecyclerAdapter(team.Players, false, SelectionOption.NO_SELECT)
            playerRecycler.adapter = playerRecyclerAdapter
        }
    }

    /*
    Companion static class for fragment
     */
    companion object {

        private val ARG_TEAM = "TEAM_ARG"
        private val ARG_TAG = "TAG"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param team : team parameter with player data
         * @param tag:
         * @return A new instance of fragment SingleTeamFragment.
         */
        @JvmStatic
        fun newInstance(team : Team, tag : String) =
                SingleTeamFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_TEAM, team)
                        putString(ARG_TAG, tag)
                    }
                }
    }
}
