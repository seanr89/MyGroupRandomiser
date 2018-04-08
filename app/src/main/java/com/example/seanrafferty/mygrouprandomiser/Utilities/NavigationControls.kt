package com.example.seanrafferty.mygrouprandomiser.Utilities

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.example.seanrafferty.mygrouprandomiser.MainActivity


/**
 * IN DEV - Class to provide static methods for activity navigation across the site
 */
class NavigationControls {

    companion object {
        fun NavigateHome(context : Context)
        {
            val intent= Intent(context, MainActivity::class.java)
            startActivity(context, intent, null)
        }
    }
}