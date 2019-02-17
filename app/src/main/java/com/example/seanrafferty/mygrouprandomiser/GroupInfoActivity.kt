package com.example.seanrafferty.mygrouprandomiser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.GroupEventRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.EventDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.MyGroupDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import com.example.seanrafferty.mygrouprandomiser.Utilities.SelectionOption

/**
 * Activity to display and handle GroupInfo details
 */
class GroupInfoActivity : AppCompatActivity() {

    private lateinit var viewManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    lateinit var SelectedGroup : MyGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)

        val ID = intent.getStringExtra("GroupID").toInt()
        //now we need to request data from the DB for the passed in ID
        SelectedGroup = GetGroupFromDB(ID)

        //now refresh the data with selected group details
        RefreshMyGroupInfo(SelectedGroup)

        //Create Button Listener to navigate
        var btn_assign_players = findViewById<Button>(R.id.btn_assign_players)
        btn_assign_players.setOnClickListener()
        {
            NavigationControls.NavigateToPlayerAssignment(this, ID)
        }
        var btn_create_event = findViewById<Button>(R.id.btn_create_event)
        btn_create_event.setOnClickListener()
        {
            NavigationControls.NavigateToGroupCreateEventActivity(this, ID)
        }

        //initialise the event manager and request all events for the group
        var eventManager = EventManager(this)
        var groupEvents = eventManager.GetAllEventsForAGroup(SelectedGroup)

        //SEAN - Remove all completed events
        //SEAN - Remove all completed events

        var recyclerAdapter = GroupEventRecyclerAdapter(groupEvents, this, SelectionOption.SINGLE_SELECT)
        viewManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.GroupInfoRecycler).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = recyclerAdapter
        }
    }

    /**
     * Request Group Info for the database again
     * @param id - the MyGroup Identification
     * @return A MyGroup object
     */
    private fun GetGroupFromDB(id: Int) : MyGroup
    {
        var groupManager = MyGroupManager(this)
        return groupManager.ReadGroupByID(id)
    }

    /**
     * Refresh the screen details for the group provided
     * @param group - the MyGroup object
     */
    private fun RefreshMyGroupInfo(group : MyGroup)
    {
        val groupNameTextView = findViewById<TextView>(R.id.groupNameView)
        groupNameTextView.text = group.Name
        RefreshGroupInfoStats(group)
    }

    /**
     * Handle the refreshing of player and event count stats for an individual group
     * @param group : the group to query
     */
    private fun RefreshGroupInfoStats(group : MyGroup)
    {
        var playerCount = MyGroupDBHandler(DatabaseHandler(this)).GetPlayerCountAssignedToGroup(group)
        var eventCount = EventDBHandler(DatabaseHandler(this)).GetCountOfEventsForGroup(group)

        val groupEventCountTextView = findViewById<TextView>(R.id.groupEventCountView)
        groupEventCountTextView.text = eventCount.toString()
        val groupPlayerCountTextView = findViewById<TextView>(R.id.groupPlayerCountView)
        groupPlayerCountTextView.text = playerCount.toString()
    }
}
