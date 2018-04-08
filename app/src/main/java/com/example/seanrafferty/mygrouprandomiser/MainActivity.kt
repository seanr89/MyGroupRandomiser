package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.GroupAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler

class MainActivity : AppCompatActivity()
{
    lateinit var _GroupListView : ListView;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _GroupListView = findViewById(R.id.GroupListView) as ListView

        //Instantiate the and create listening for request groups button click event
        var btn_request_groups = findViewById<Button>(R.id.btnRequestGroups)
        btn_request_groups.setOnClickListener {

            // Handler code here. - request all stored MyGroup objects and append these to a listview adapter
            var groupList = RequestGroups()
            var groupAdapter = GroupAdapter(this, groupList)
            _GroupListView.adapter = groupAdapter;
        }
        var btn_add_group = findViewById<Button>(R.id.btnAddGroup)
        btn_add_group.setOnClickListener()
        {
            AccessEditGroupActivity()
        }

        //Handle listview item selection
        _GroupListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            // Handle group item selection and ensure object casting
            var selectedGroup = _GroupListView.adapter.getItem(position) as MyGroup
            Log.d("ListView Selected","${selectedGroup.toString()} selected")

            NavigateToGroupInfoActivity(selectedGroup)
        }
    }

    /**
     * handle creation of option action overflow on view
     * with xml menu option file targetted
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_overflow, menu)
        return true
    }

    /**
     * overridden handle action overflow item selection events
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
            when(item.itemId)
            {
                R.id.action_players -> {
                    NavigateToPlayersActivity()
                    return true
                }
                R.id.action_settings -> {
                    Toast.makeText(this, "Settings not available", Toast.LENGTH_LONG).show()
                }
            }
        return true
    }

    /**
     * Initialise Screen Contents
     */
    fun InitialiseScreen()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)

        InitialiseDB()
        Toast.makeText(this, "Database Initialised", Toast.LENGTH_LONG).show()
    }

    /**
     * Request all stored groups available
     * returns @param grouplist - An arraylist of MyGroup objects
     */
    fun RequestGroups() : ArrayList<MyGroup>
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)

        //initialise an ArrayList and a DatabaseHandler object
        var groupList = ArrayList<MyGroup>();
        var dbHandler = DatabaseHandler(this)

        //Request all groups from the database and return the data
        groupList = dbHandler.ReadAllGroups()
        return groupList
    }

    /**
     * Base operation to initialise the database and create/update the database
     */
    fun InitialiseDB()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)
        var DB:DatabaseHandler = DatabaseHandler(this);
    }

    /**
     * Operation to start and display the EditGroup Activity
     */
    fun AccessEditGroupActivity()
    {
        var intent= Intent(this,AddGroup::class.java)
        startActivity(intent)
    }

    /**
     * create and start the player activity
     */
    fun NavigateToPlayersActivity()
    {
        var intent= Intent(this,PlayerActivity::class.java)
        startActivity(intent)
    }

    /**
     * Instantiate and navigate to the GroupInfo activity
     */
    fun NavigateToGroupInfoActivity(group : MyGroup)
    {
        Log.d("MainActivity",object{}.javaClass.enclosingMethod.name)

        var intent= Intent(this,GroupInfoActivity::class.java)
        intent.putExtra("GroupID", group.ID)
        startActivity(intent)
    }
}


