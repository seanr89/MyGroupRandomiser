package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.PlayerAdapter
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler

/**
 * New activity to handle the assignment of new players to a group!
 */
class PlayerAssigmentActivity : AppCompatActivity()
{
    lateinit var _PlayerDBHandler : PlayerDBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_assigment)

        var groupID = intent.getStringExtra("GroupID").toInt()

        var btn_save_selected = findViewById<Button>(R.id.btn_save_selected)
        btn_save_selected.setOnClickListener()
        {
            //Toast.makeText(this, "Not Yet Available!!", Toast.LENGTH_LONG).show()
            SaveSelectedAssignments()
        }

        _PlayerDBHandler = PlayerDBHandler(DatabaseHandler(this))

        val playerRecycler = findViewById<RecyclerView>(R.id.PlayerRecycler)
        val UnassignedPlayers = _PlayerDBHandler.GetAllPlayersNotAssignedToGroup(MyGroup(groupID, ""))
        var playerAdapter = PlayerRecyclerAdapter(UnassignedPlayers as ArrayList<Player>)

        playerRecycler.adapter = playerAdapter
    }


    /**
     * Operation to handle the saving of details
     */
    fun SaveSelectedAssignments()
    {
        Log.d("Method",object{}.javaClass.enclosingMethod.name)
    }
}
