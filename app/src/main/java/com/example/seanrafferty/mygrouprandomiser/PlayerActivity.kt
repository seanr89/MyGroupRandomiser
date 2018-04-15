package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.example.seanrafferty.mygrouprandomiser.Adapters.GroupAdapter
import com.example.seanrafferty.mygrouprandomiser.Adapters.PlayerAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
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
            NavigationControls.Companion.NavigateToAddPlayerActivity(this)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        /**
         * initialise the player ListView, request all players and append into an adapter
         */
        _PlayerListView = findViewById(R.id.PlayerListView) as ListView
        var playerList = RequestAllPlayers()
        var playerAdapter = PlayerAdapter(this, playerList)
        _PlayerListView.adapter = playerAdapter;
    }

    /**
     * Request all the current players from the interal data source
     * @return an array of players
     */
    fun RequestAllPlayers() : ArrayList<Player>
    {
        Log.d("PlayerActivity", object{}.javaClass.enclosingMethod.name)

        //initialise an ArrayList and a DatabaseHandler object
        var playerList: ArrayList<Player>;
        var DB = DatabaseHandler(this)

        //query DB for all players and return
        playerList = DB.ReadAllPlayers()

        return playerList
    }
}
