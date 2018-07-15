package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls


class AddGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_group)

        //initialise the button and setup the click event
        val btn_save_group = findViewById<Button>(R.id.btn_save_group)
        btn_save_group.setOnClickListener()
        {
            val result = CreateAndSaveGroup()

            if(result >= 1)
            {
                NavigationControls.NavigateToMainActivity(this)
            }
            else
            {
                Toast.makeText(this, "Error - Save Failed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Create and Save a group to the DB
     * @returns An integer to denote the status of the insert event
     */
    fun CreateAndSaveGroup() : Int
    {
        val nameTextView = findViewById<TextView>(R.id.nameText)
        val name : String = nameTextView.text.toString()

        if(!name.isNullOrBlank())
        {
            var groupManager = MyGroupManager(this)
            return groupManager.CreateGroup(MyGroup(0, name))
        }
        else
        {
            Toast.makeText(this, "No Name Provided", Toast.LENGTH_SHORT).show()
            return -1
        }
    }
}
