package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.*
import com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters.PlayerSkillRecyclerAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.PlayerManager
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import com.example.seanrafferty.mygrouprandomiser.Utilities.SelectionOption


class AddPlayerActivity : AppCompatActivity() {

    lateinit var _RatingSpinner : Spinner
    lateinit var _SavePlayerBtn : Button
    lateinit var _playerSkillRecyclerAdapter : PlayerSkillRecyclerAdapter<PlayerSkill>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)

        //Initialise the player rating system (count is 1 to 100)
        val ratings = Array(100) { i -> (i + 1) }
        _RatingSpinner = findViewById(R.id.spinnerRating)

        var spinnerAdapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, ratings)
        _RatingSpinner.adapter = spinnerAdapter

        _SavePlayerBtn = findViewById(R.id.btn_save_player)
        _SavePlayerBtn.setOnClickListener()
        {
            var id = SaveNewPlayer()
            //Run save player and check that the return is a unique ID
            if(id >= 1)
            {
                SavePlayerSkillsToPlayer(id)
                NavigationControls.NavigateToPlayerActivity(this)
            }
        }

        //Initialise the recycler view for the player skills data to be displayed
        val linearManager = LinearLayoutManager(this)
        val playerManager = PlayerManager(this)
        _playerSkillRecyclerAdapter = PlayerSkillRecyclerAdapter<PlayerSkill>(playerManager.ReadAllAvailablePlayerSkills(),
                SelectionOption.MULTI_SELECT)
        findViewById<RecyclerView>(R.id.recyclerPlayerSkills).apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = linearManager
            // specify an viewAdapter (see also next example)
            adapter = _playerSkillRecyclerAdapter
        }
    }

    /**
     * Read player details and save content to the internal DB
     * @return the ID of the newly inserted player, or -1 if an error occurred
     */
    private fun SaveNewPlayer() : Int
    {
        //Log.d("TAG", object{}.javaClass.enclosingMethod.name)
        val nameTextView = findViewById<TextView>(R.id.playerNameEditView)
        val name : String = nameTextView.text.toString()
        val rating = _RatingSpinner.selectedItem

        if(name.isNotEmpty())
        {
            val playerManager = PlayerManager(this)
            return playerManager.SavePlayer(Player(0, name, rating.toString().toDouble()))
        }
        Toast.makeText(this, "No Name Provided", Toast.LENGTH_LONG).show()
        return 0
    }

    /**
     * read the selected player skills and save to the player as a mapping
     */
    private fun SavePlayerSkillsToPlayer(playerID :Int)
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        var selectedSkills = _playerSkillRecyclerAdapter.SelectedItems
        if(selectedSkills.isNotEmpty())
        {
            val playerManager = PlayerManager(this)
            playerManager.SavePlayerSkillsToPlayer(selectedSkills, Player(playerID, ""))
        }
    }
}
