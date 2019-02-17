package com.example.seanrafferty.mygrouprandomiser.Utilities

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.seanrafferty.mygrouprandomiser.*

/**
 * Provides static methods for activity navigation across the app
 */
class NavigationControls {

    companion object
    {
        /**
         * Navigation to the player assignment screen
         * @param context : current activity context navigating from
         * @param groupID : the id of the group current a part of
         */
        fun NavigateToPlayerAssignment(context: Context, groupID : Int)
        {
            val intent= Intent(context, PlayerAssigmentActivity::class.java)
            intent.putExtra("GroupID", groupID.toString())
            startActivity(context, intent, null)
        }

        /**
         * Navigation to the GroupInfo Activity
         * @param context : current activity context navigating from
         * @param groupID : the current GroupID selected
         */
        fun NavigateToGroupInfoActivity(context: Context, groupID : Int)
        {
            val intent= Intent(context, GroupInfoActivity::class.java)
            intent.putExtra("GroupID", groupID.toString())
            startActivity(context, intent, null)
        }

        /**
         * Navigate and start the Main Activity
         * @param context : current activity context navigating from
         */
        fun NavigateToMainActivity(context: Context)
        {
            val intent= Intent(context, MainActivity::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the player activity
         * @param context : current activity context navigating from
         */
        fun NavigateToPlayerActivity(context: Context)
        {
            var intent= Intent(context, PlayerActivity::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the Edit/Add group Activity
         * @param context : current activity context navigating from
         */
        fun NavigateToEditGroupActivity(context: Context)
        {
            var intent= Intent(context, AddGroup::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the Add Player Activity
         * @param context : current activity context navigating from
         */
        fun NavigateToAddPlayerActivity(context: Context)
        {
            var intent= Intent(context, AddPlayerActivity::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the MyGroup Create Event Activity
         * @param context : current activity context navigating from
         * @param groupID : unique id for a group
         */
        fun NavigateToGroupCreateEventActivity(context: Context, groupID: Int)
        {
            var intent= Intent(context, GroupEventGeneratorActivity::class.java)
            intent.putExtra("GroupID", groupID.toString())
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the event info page
         * @param context : current activity context navigating from
         * @param eventID : the event ID to review stats
         */
        fun NavigateToEventInfoActivity(context: Context, eventID: Int)
        {
            var intent= Intent(context, EventInfoActivity::class.java)
            intent.putExtra("EventID", eventID.toString())
            startActivity(context, intent, null)
        }

    }
}