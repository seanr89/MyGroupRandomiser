package com.example.seanrafferty.mygrouprandomiser.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Team

import com.example.seanrafferty.mygrouprandomiser.R


/**
 * A simple [Fragment] subclass.
 * Use the [SingleTeamFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SingleTeamFragment : Fragment() {
    // Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_single_team, container, false)
    }


    companion object {

        private val ARG_TEAM = "TEAM_ARG"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingleTeamFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(team : Team) =
                SingleTeamFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_TEAM, team)
                    }
                }
    }
}
