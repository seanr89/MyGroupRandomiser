package com.example.seanrafferty.mygrouprandomiser

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

        InitialiseScreen()

        _GroupListView = findViewById(R.id.GroupListView) as ListView

        //Instantiate the and create listening for request groups button click event
        var btn_request_groups = findViewById(R.id.btnRequestGroups) as Button
        btn_request_groups.setOnClickListener {

            // Handler code here.
            var groupList = RequestGroups();
            var groupAdapter = GroupAdapter(this, groupList);
            _GroupListView.adapter = groupAdapter;
        }
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
     */
    fun RequestGroups() : ArrayList<MyGroup>
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)

        var groupList = ArrayList<MyGroup>();
        var dbHandler = DatabaseHandler(this);

        groupList = dbHandler.ReadAllGroups();

        return groupList
    }

    fun InitialiseDB()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)
        var DB:DatabaseHandler = DatabaseHandler(this)
    }
}


