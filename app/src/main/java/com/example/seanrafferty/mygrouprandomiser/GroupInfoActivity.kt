package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler

class GroupInfoActivity : AppCompatActivity() {

    lateinit var SelectedGroup : MyGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)

        val ID = intent.getStringExtra("GroupID").toInt()

        //now we need to request data from the DB
        SelectedGroup = GetGroupFromDB(ID)

    }

    /**
     * 
     */
    private fun GetGroupFromDB(id: Int) : MyGroup
    {
        var db = DatabaseHandler(this)

        return db.ReadMyGroupByID(id)
    }
}
