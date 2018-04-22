package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker

import com.example.seanrafferty.mygrouprandomiser.R
import java.text.SimpleDateFormat
import java.util.*


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
    private var listener: OnFragmentInteractionListener? = null
    private var _GroupID : Int = 0;

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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     *
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
     *
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
     *
     */
    fun GetSelectedPlayers()
    {

    }

    /**
     *
     */
    fun ResetFragmentContent()
    {

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
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventSetupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EventSetupFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
