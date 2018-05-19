package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.MyGroupDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import java.security.acl.Group

class AddGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_group)

        val btn_save_group = findViewById(R.id.btn_save_group) as Button
        btn_save_group.setOnClickListener()
        {
            val result = CreateAndSaveGroup()

            if(result >= 1)
            {
                NavigationControls.NavaigateToMainActivity(this)
            }
            else
            {
                Toast.makeText(this, "Sorry - Saving Failed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Create and Save a group the DB
     * @returns : integer to denote the status of the insert event
     */
    fun CreateAndSaveGroup() : Int
    {
        var result: Int

        val nameTextView = findViewById<TextView>(R.id.nameText)
        val name : String = nameTextView.text.toString()

        var groupManager = MyGroupManager(this)

        result = groupManager.CreateGroup(MyGroup(0, name))

        return result
    }
}
