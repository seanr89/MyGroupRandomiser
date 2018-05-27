package com.example.seanrafferty.mygrouprandomiser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.example.seanrafferty.mygrouprandomiser.Adapters.PlayerAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls

import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    lateinit var _PlayerListView : ListView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        //setSupportActionBar(toolbar)

        /**
         * Create listener on + item click event
         */
        fab.setOnClickListener { view ->
            NavigationControls.NavigateToAddPlayerActivity(this)
        }

        /**
         * initialise the player ListView, request all players and append into an adapter
         */
        _PlayerListView = findViewById(R.id.PlayerListView)
        var playerList = RequestAllPlayers()
        var playerAdapter = PlayerAdapter(this, playerList)
        _PlayerListView.adapter = playerAdapter
    }

    /**
     * Request all the current players from the interal data source
     * @return an array of players
     */
    fun RequestAllPlayers() : ArrayList<Player>
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        //initialise the play database
        var playerDB = PlayerDBHandler(DatabaseHandler(this))
        //query DB for all players and return
        return playerDB.ReadAllPlayers()
    }
}
