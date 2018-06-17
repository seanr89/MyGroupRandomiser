package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.os.Bundle
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.R
import android.content.DialogInterface
import android.app.*
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.widget.Toast


/*
https://gist.github.com/nosix/423b1e5a7d6c5837c1e12fab4d7f6bfd
https://guides.codepath.com/android/using-dialogfragment
 */

/**
 * dialog box used to display the shuffle options available
 */
class ShuffleUpDialog : DialogFragment()
{
    private var mCallback: RandomisationSelectedListener? = null
    private val TAG = "ShuffleUpDialog"

    /**
     * triggered before the view is created and displayed
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShuffleUpDialog.RandomisationSelectedListener) {
            mCallback = context
        } else {
            throw RuntimeException(context.toString() + " must implement RandomisationSelectedListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog
    {
        // Use the Builder class for convenient dialog construction
        var dialog = AlertDialog.Builder(activity)

        // 2. Chain together various setter methods to set the dialog characteristics
        dialog.setTitle(R.string.shuffle_dialog_title)

        // Add the buttons for selecting cancel
        dialog.setNegativeButton(R.string.shuffle_cancel, DialogInterface.OnClickListener { dialog, id ->
            // User clicked Cancel button
            Toast.makeText(context, "Cancel!", Toast.LENGTH_LONG).show()
            dialog?.cancel()
        })

        //Initialise the list view dialog array
        dialog.setItems(R.array.shuffleoptions, DialogInterface.OnClickListener{ dialog, id ->
            // User clicked Cancel button
            Toast.makeText(context, "Selected!", Toast.LENGTH_LONG).show()

            var items = this.resources.getStringArray(R.array.shuffleoptions);
            var selectedItem = items[id]

            handleActionSelectedFromItems(selectedItem)
        })

        return dialog.create().apply { setCanceledOnTouchOutside(false) }
    }

    /**
     * handle the triggering of said shuffle event
     * @param itemName
     */
    private fun handleActionSelectedFromItems(itemName : String)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        when(itemName)
        {
            "Randomize" -> onRandomShuffleSelected()
            "Shuffle" -> onRatingShuffleSelected()
            else ->
            {
                Toast.makeText(context, "Unable to process", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * operation to trigger parent fragment event to shuffle players randomly
     */
    private fun onRandomShuffleSelected()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        mCallback!!.shufflePlayersRandomly()
    }

    /**
     * operation to trigger parent fragment event to shuffle players by rating
     */
    private fun onRatingShuffleSelected()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        mCallback!!.shufflePlayersByRating()
    }


    /**
     * Defines the listener interface controls to communicate back to the
     */
    // Defines the listener interface
    interface RandomisationSelectedListener {
        //void onFinishEditDialog(String inputText);
        fun shufflePlayersRandomly()
        fun shufflePlayersByRating()
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    /**
     * Static object to provide event initialisation and display
     */
    companion object {
        val TAG = ShuffleUpDialog::class.qualifiedName

        fun show(fragment: FragmentActivity?)
        {
            Log.d(TAG, object{}.javaClass.enclosingMethod.name)
            ShuffleUpDialog().apply {

            }.show(fragment!!.fragmentManager, TAG)
        }
    }

}