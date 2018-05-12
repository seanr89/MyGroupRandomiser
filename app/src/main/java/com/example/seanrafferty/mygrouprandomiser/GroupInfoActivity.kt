package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.GroupEventRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
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

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView
    lateinit var SelectedGroup : MyGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)

        val ID = intent.getStringExtra("GroupID").toInt()
        //now we need to request data from the DB for the passed in ID
        SelectedGroup = GetGroupFromDB(ID)

        //now refresh the data
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

        var btn_event_stats = findViewById<Button>(R.id.btn_event_stats)
        btn_event_stats.setOnClickListener()
        {
            Toast.makeText(this, "Not Yet Available!!", Toast.LENGTH_LONG).show()
            //NavigationControls.NavigateToEventStatsActivity(this,)
        }

        var eventManager = EventManager(this)
        var groupEvents = eventManager.GetAllEventsForAGroup(SelectedGroup)

        var recyclerAdapter = GroupEventRecyclerAdapter(groupEvents, this, SelectionOption.SINGLE_SELECT)
        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.GroupInfoRecycler).apply{
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
        var MyGroupDB = MyGroupDBHandler(DatabaseHandler(this))
        return MyGroupDB.ReadMyGroupByID(id)
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
        var eventCount = EventDBHandler(DatabaseHandler(this)).GetCountOfEventsForGrouo(group)

        val groupEventCountTextView = findViewById<TextView>(R.id.groupEventCountView)
        groupEventCountTextView.text = eventCount.toString()
        val groupPlayerCountTextView = findViewById<TextView>(R.id.groupPlayerCountView)
        groupPlayerCountTextView.text = playerCount.toString()
    }
}
