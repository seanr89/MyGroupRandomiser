package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class EventStatsActivity : AppCompatActivity() {

    private var _GroupID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_stats)

        _GroupID = intent.getStringExtra("GroupID").toInt()
    }
}
