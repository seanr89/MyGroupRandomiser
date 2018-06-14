package com.example.seanrafferty.mygrouprandomiser.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

/*
https://gist.github.com/nosix/423b1e5a7d6c5837c1e12fab4d7f6bfd
https://guides.codepath.com/android/using-dialogfragment
 */

class ShuffleUpDialog : DialogFragment()
{

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog
    {
        var dialog = AlertDialog.Builder(activity)
        return dialog.create()
    }
}