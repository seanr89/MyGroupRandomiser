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

    //Recycler manager details
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var _PlayerRecycler : RecyclerView
    private lateinit var _PlayerAdapter : PlayerRecyclerAdapter

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

        viewManager = LinearLayoutManager(activity)
        _PlayerRecycler = view.findViewById<RecyclerView>(R.id.TeamPlayerRecycler).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = _PlayerAdapter
        }

        return view
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

    /**
     * Ah this could be the to handle any and all calls
     */
    override fun onAttach(context: Context)
    {
        Log.d("TeamFragment", object{}.javaClass.enclosingMethod.name)
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        _PlayerAdapter = PlayerRecyclerAdapter(arrayListOf(), true)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     *
     */
    fun UpdateRecyclerAdapter(team: Team)
    {
        if(_PlayerAdapter == null)
        {
            _PlayerAdapter = PlayerRecyclerAdapter(team.Players, true)
        }
        else
        {
            _PlayerAdapter.playerList = team.Players
            _PlayerAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Handle the refreshing of team player content
     * @param team : the currently generated team
     */
    fun RefreshTeam(team : Team)
    {
        Log.d("Method", object{}.javaClass.enclosingMethod.name)
        UpdateRecyclerAdapter(team)
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

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment TeamFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                TeamFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}
