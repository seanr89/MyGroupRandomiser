package com.example.seanrafferty.mygrouprandomiser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.example.seanrafferty.mygrouprandomiser.Adapters.PlayerAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.PlayerManager
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls

import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        /**
         * Create listener on + item click event
         */
        fab.setOnClickListener { view ->
            NavigationControls.NavigateToAddPlayerActivity(this)
        }

        /**
         * initialise the player ListView, request all players, append into an adapter and add to listview
         */
        val playerListView = this.findViewById<ListView>(R.id.PlayerListView)
        val playerList = requestAllPlayers()
        val playerAdapter = PlayerAdapter(this, playerList)
        playerListView.adapter = playerAdapter
    }

    /**
     * Request all the current players from the interal data source
     * @return an array of players
     */
    fun requestAllPlayers() : ArrayList<Player>
    {
        //Initialise the player manager and request all players
        var playerManager = PlayerManager(this)
        return playerManager.ReadAllPlayers()
    }
}
