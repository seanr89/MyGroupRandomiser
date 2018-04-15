package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls


class AddPlayerActivity : AppCompatActivity() {

    lateinit var _RatingSpinner : Spinner
    lateinit var _SavePlayerBtn : Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)

        val ratings = Array(10, { i -> (i + 1) })

        _RatingSpinner = findViewById(R.id.spinnerRating)

        val spinnerAdapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, ratings)
        _RatingSpinner.adapter = spinnerAdapter

        _SavePlayerBtn = findViewById(R.id.btn_save_player)
        _SavePlayerBtn.setOnClickListener()
        {
            SaveNewPlayer()
            NavigationControls.Companion.NavigateToPlayerActivity(this)
        }
    }

    /**
     * Read player details and save content to the internal DB
     */
    private fun SaveNewPlayer() : Int
    {
        Log.d("AddPlayerActivity", object{}.javaClass.enclosingMethod.name)

        var result : Int
        val nameTextView = findViewById(R.id.playerNameEditView) as TextView
        val name : String = nameTextView.text.toString()
        val rating = _RatingSpinner.selectedItem

        var DB = DatabaseHandler(this)

        result = DB.InsertPlayer(Player(0, name, rating.toString().toInt()))

        return result
    }
}
