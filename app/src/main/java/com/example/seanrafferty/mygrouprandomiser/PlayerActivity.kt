package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setSupportActionBar(toolbar)

        /**
         * Create listener on + item click event
         */
        fab.setOnClickListener { view ->
                AddPlayerActivity()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        RequestAllPlayers()
    }

    /**
     * Request all the current players from the interal data source
     */
    fun RequestAllPlayers()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)
    }

    /**
     * Start and Navigate to the Add Player activity
     */
    fun AddPlayerActivity()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)

        var intent= Intent(this,AddPlayerActivity()::class.java)
        startActivity(intent)

    }
}
