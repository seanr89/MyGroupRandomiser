package com.example.seanrafferty.mygrouprandomiser

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Business.PlayerManager
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls

/**
 * New activity to handle the assignment of new players to a group!
 */
class PlayerAssigmentActivity : AppCompatActivity()
{
    private lateinit var viewManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    var groupID : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_assigment)

        groupID = intent.getStringExtra("GroupID").toInt()
        RequestAndDisplayGroupNameByID(groupID, this)

        var btn_save_selected = findViewById<Button>(R.id.btn_save_selected)
        btn_save_selected.setOnClickListener()
        {
            //Toast.makeText(this, "Not Yet Available!!", Toast.LENGTH_LONG).show()
            SaveSelectedAssignments()
            NavigationControls.NavigateToGroupInfoActivity(this, groupID)
        }

        val unassignedPlayers = MyGroupManager(this).ReadAllPlayersNotAssignedToGroup(MyGroup(groupID, ""))
        if(unassignedPlayers.isNotEmpty())
        {
            var playerAdapter = PlayerRecyclerAdapter(unassignedPlayers as ArrayList<Player>, true)

            viewManager = androidx.recyclerview.widget.LinearLayoutManager(this)
            recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.PlayerRecycler).apply{
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)
                // use a linear layout manager
                layoutManager = viewManager
                // specify an viewAdapter (see also next example)
                adapter = playerAdapter
            }
        }
        else
        {
            Toast.makeText(this, "No Players Found!", Toast.LENGTH_LONG).show()
        }

    }

    /**
     * Handle the requesting and displaying of group name info on the activity
     * @param groupID : group ID to query the group for
     * @param context : the current activity context
     */
    private fun RequestAndDisplayGroupNameByID(groupID : Int, context : Context?)
    {
        var groupManager = MyGroupManager(context)
        var currentGroup = groupManager.ReadGroupByID(groupID);

        var txtViewGroupName = findViewById<TextView>(R.id.txtViewGroupName)
        txtViewGroupName.text = currentGroup.Name
    }


    /**
     * Operation to handle the saving of selected players to the provided group
     * GroupID is found at load time
     */
    private fun SaveSelectedAssignments()
    {
        //Log.d("TAG", object{}.javaClass.enclosingMethod.name)
        var adapter = recyclerView.adapter as PlayerRecyclerAdapter
        var players = adapter.SelectedItems

        if(!players.isEmpty())
        {
            var playerManager = PlayerManager(this)
            playerManager.AssignPlayersToGroup(players, MyGroup(groupID, ""))
            Toast.makeText(this, "Players Assigned", Toast.LENGTH_LONG).show()
        }
    }
}
