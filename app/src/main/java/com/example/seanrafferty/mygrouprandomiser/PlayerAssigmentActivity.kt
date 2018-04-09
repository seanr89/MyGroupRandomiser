package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

/**
 * New activity to handle the assignment of new players to a group!
 */
class PlayerAssigmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_assigment)

        var btn_save_selected = findViewById<Button>(R.id.btn_save_selected)
        btn_save_selected.setOnClickListener()
        {
            Toast.makeText(this, "Not Yet Available!!", Toast.LENGTH_LONG).show()
        }
    }


    fun SaveSelectedAssignments()
    {
        Log.d("Method",object{}.javaClass.enclosingMethod.name)
    }
}
