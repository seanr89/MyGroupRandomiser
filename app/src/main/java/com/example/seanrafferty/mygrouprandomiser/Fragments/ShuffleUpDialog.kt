package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.os.Bundle
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.R
import android.content.DialogInterface
import android.app.*
import android.support.v4.app.FragmentActivity
import android.widget.Toast


/*
https://gist.github.com/nosix/423b1e5a7d6c5837c1e12fab4d7f6bfd
https://guides.codepath.com/android/using-dialogfragment
 */

/**
 *
 */
class ShuffleUpDialog : DialogFragment()
{
    private val TAG = "ShuffleUpDialog"

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog
    {
        // Use the Builder class for convenient dialog construction
        var dialog = AlertDialog.Builder(activity)

        // 2. Chain together various setter methods to set the dialog characteristics
        dialog.setMessage(R.string.shuffle_dialog_message)
                .setTitle(R.string.shuffle_dialog_title)

        // Add the buttons for selecting cancel
        dialog.setNegativeButton(R.string.shuffle_cancel, DialogInterface.OnClickListener { dialog, id ->
            // User clicked Cancel button
            Toast.makeText(context, "Cancel!", Toast.LENGTH_LONG).show()
            dialog?.cancel()
        })

        //Initialise the list view dialog array
        dialog.setItems(R.array.ShuffleOptions, DialogInterface.OnClickListener{ dialog, id ->
            // User clicked Cancel button
            Toast.makeText(context, "Selected!", Toast.LENGTH_LONG).show()

            var items = view.getResources().getStringArray(R.array.ShuffleOptions);
            var selectedItem = items[id]

            handleActionSelectedFromItems(selectedItem)
        })

//        dialog.setNeutralButton(R.string.shuffle_randomize, DialogInterface.OnClickListener { dialog, id ->
//            // User clicked Randomize button
//            Toast.makeText(context, "Randomize!", Toast.LENGTH_LONG).show()
//            onRandomShuffleSelected()
//        })
//
//        dialog.setNeutralButton(R.string.shuffle_rating, DialogInterface.OnClickListener { dialog, id ->
//            // User clicked Rating button
//            Toast.makeText(context, "Rating!", Toast.LENGTH_LONG).show()
//            onRatingShuffleSelected()
//        })

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
     *
     */
    private fun onRandomShuffleSelected()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }

    /**
     *
     */
    private fun onRatingShuffleSelected()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }


    /**
     * Defines the listener interface controls to communicate back to the
     */
    // Defines the listener interface
    interface ShuffleOptionRandomiseListener {
        //void onFinishEditDialog(String inputText);
        fun shufflePlayersRandomly()
    }

    /**
     *
     */
    interface ShuffleOptionRatingsListener
    {
        fun shufflePlayersByRating()
    }

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