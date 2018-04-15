package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls

/**
 * Activity to display and handle GroupInfo details
 */
class GroupInfoActivity : AppCompatActivity() {

    lateinit var SelectedGroup : MyGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)

        val ID = intent.getStringExtra("GroupID").toInt()

        //now we need to request data from the DB
        SelectedGroup = GetGroupFromDB(ID)

        //now refresh the data
        RefreshMyGroupInfo(SelectedGroup)

        //Create Button Listener to navigate
        var btn_assign_players = findViewById<Button>(R.id.btn_assign_players)
        btn_assign_players.setOnClickListener()
        {
            NavigationControls.Companion.NavigateToPlayerAssignment(this, ID)
        }
    }

    /**
     * Request Group Info for the database again
     * @param id - the MyGroup Identification
     * @return A MyGroup object
     */
    private fun GetGroupFromDB(id: Int) : MyGroup
    {
        var db = DatabaseHandler(this)
        return db.ReadMyGroupByID(id)
    }

    /**
     * Refresh the screen details for the group provided
     * @param group - the MyGroup object
     */
    private fun RefreshMyGroupInfo(group : MyGroup)
    {
        val groupIDTextView = findViewById<TextView>(R.id.groupIDView)
        val groupNameTextView = findViewById<TextView>(R.id.groupNameView)

        groupIDTextView.text = group.ID.toString()
        groupNameTextView.text = group.Name
    }
}
