package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
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
    lateinit var _PlayerDBHandler : PlayerDBHandler
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView
    var groupID : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_assigment)

        groupID = intent.getStringExtra("GroupID").toInt()

        var btn_save_selected = findViewById<Button>(R.id.btn_save_selected)
        btn_save_selected.setOnClickListener()
        {
            //Toast.makeText(this, "Not Yet Available!!", Toast.LENGTH_LONG).show()
            SaveSelectedAssignments()
            NavigationControls.NavigateToGroupInfoActivity(this, groupID)
        }

        _PlayerDBHandler = PlayerDBHandler(DatabaseHandler(this))
        val unassignedPlayers = MyGroupManager(this).ReadAllPlayersNotAssignedToGroup(MyGroup(groupID, ""))

        if(unassignedPlayers.isNotEmpty())
        {
            var playerAdapter = PlayerRecyclerAdapter(unassignedPlayers as ArrayList<Player>, true)

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
        else
        {
            Toast.makeText(this, "No Players Found!", Toast.LENGTH_LONG).show()
        }

    }


    /**
     * Operation to handle the saving of selected players to the provided group
     * GroupID is found at load time
     */
    private fun SaveSelectedAssignments()
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        var adapter = recyclerView.adapter as PlayerRecyclerAdapter
        var players = adapter.SelectedItems

        if(!players.isEmpty())
        {
            _PlayerDBHandler.AssignPlayersToGroup(players, MyGroup(groupID, ""))
            Toast.makeText(this, "Players Assigned", Toast.LENGTH_LONG).show()
        }
    }
}
