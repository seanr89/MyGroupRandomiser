package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instantiate the and create listening for request groups button click event
        var btn_request_groups = findViewById(R.id.btnRequestGroups) as Button
        btn_request_groups.setOnClickListener {
            // Handler code here.
            RequestGroups();
        }
    }

    /**
     * Initialise Screen Contents
     */
    fun InitialiseScreen()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)

        InitialiseDB()
    }

    /**
     * Request all stored groups available
     */
    fun RequestGroups()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)
    }

    fun InitialiseDB()
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)
        var DB:DatabaseHandler = DatabaseHandler(this);


    }
}


