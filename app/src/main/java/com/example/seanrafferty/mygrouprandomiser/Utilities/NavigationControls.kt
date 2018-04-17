package com.example.seanrafferty.mygrouprandomiser.Utilities

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.example.seanrafferty.mygrouprandomiser.*


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

        /**
         * Navigation to the player assignment screen
         */
        fun NavigateToPlayerAssignment(context: Context, groupID : Int)
        {
            val intent= Intent(context, PlayerAssigmentActivity::class.java)
            intent.putExtra("GroupID", groupID.toString())
            startActivity(context, intent, null)
        }

        fun NavigateToGroupInfoActivity(context: Context, groupID : Int)
        {
            val intent= Intent(context, GroupInfoActivity::class.java)
            intent.putExtra("GroupID", groupID.toString())
            startActivity(context, intent, null)
        }

        /**
         *  Navigate and start the Main Activity
         */
        fun NavigateToMainActivity(context: Context)
        {
            val intent= Intent(context, MainActivity::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the player activity
         */
        fun NavigateToPlayerActivity(context: Context)
        {
            var intent= Intent(context, PlayerActivity::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the Edit/Add group Activity
         */
        fun NavigateToEditGroupActivity(context: Context)
        {
            var intent= Intent(context, AddGroup::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the Add Player Activity
         */
        fun NavigateToAddPlayerActivity(context: Context)
        {
            var intent= Intent(context, AddPlayerActivity::class.java)
            startActivity(context, intent, null)
        }

        /**
         * Navigate to the MyGroup Create Event Activity
         * @param groupID : unique id for a group
         */
        fun NavigateToGroupCreateEventActivity(context: Context, groupID: Int)
        {
            var intent= Intent(context, GroupEventGeneratorActivity::class.java)
            intent.putExtra("GroupID", groupID.toString())
            startActivity(context, intent, null)
        }
    }
}