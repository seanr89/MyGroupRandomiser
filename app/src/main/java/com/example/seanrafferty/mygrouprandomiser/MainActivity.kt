package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

        //Test operation, unknown if these is really needed anymore
        //InitialiseScreen()


        _GroupListView = findViewById(R.id.GroupListView) as ListView

        //Instantiate the and create listening for request groups button click event
        var btn_request_groups = findViewById(R.id.btnRequestGroups) as Button
        btn_request_groups.setOnClickListener() {

            println("button click")
            // Handler code here. - request all stored MyGroup objects and append these to a listview adapter
            var groupList = RequestGroups()
            var groupAdapter = GroupAdapter(this, groupList)
            println("added")
            _GroupListView.adapter = groupAdapter;
        }

//        var btn_add_group = findViewById(R.id.btnAddGroup) as Button
//        btn_add_group.setOnClickListener()
//        {
//            AccessEditGroupActivity()
//        }
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
        var DB:DatabaseHandler = DatabaseHandler(this)
    }

    /**
     * Operation to start and display the EditGroup Activity
     */
    fun AccessEditGroupActivity()
    {
        var intent= Intent(this,EditGroup::class.java)
        startActivity(intent)
    }
}


