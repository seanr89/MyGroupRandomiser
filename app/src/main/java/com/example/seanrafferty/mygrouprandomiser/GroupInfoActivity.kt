package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.MyGroupDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls

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
            NavigationControls.Companion.NavigateToPlayerAssignment(this, ID)
        }

        var btn_create_event = findViewById<Button>(R.id.btn_create_event)
        btn_create_event.setOnClickListener()
        {
            NavigationControls.Companion.NavigateToGroupCreateEventActivity(this, ID)
        }

        //Setup Recycler View to view all players
        var MyGroupDBHandler = MyGroupDBHandler(DatabaseHandler(this))
        val assignedPlayers = MyGroupDBHandler.ReadAllPlayersForAGroup(MyGroup(ID, ""))
        var playerAdapter = PlayerRecyclerAdapter(assignedPlayers as ArrayList<Player>, false)
        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.PlayerRecycler).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = playerAdapter
        }
    }

    /**
     * Request Group Info for the database again
     * @param id - the MyGroup Identification
     * @return A MyGroup object
     */
    private fun GetGroupFromDB(id: Int) : MyGroup
    {
        var db = DatabaseHandler(this)
        return db.ReadMyGroupByID(id)
    }

    /**
     * Refresh the screen details for the group provided
     * @param group - the MyGroup object
     */
    private fun RefreshMyGroupInfo(group : MyGroup)
    {
        val groupIDTextView = findViewById<TextView>(R.id.groupIDView)
        val groupNameTextView = findViewById<TextView>(R.id.groupNameView)

        groupIDTextView.text = group.ID.toString()
        groupNameTextView.text = group.Name
    }

    /**
     *
     */
    private fun GetGroupPlayerandEventStats(id: Int)
    {

    }
}
