package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import java.security.acl.Group

class EditGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_group)

        val btn_save_group = findViewById(R.id.btn_save_group) as Button
        btn_save_group.setOnClickListener()
        {
            val result = CreateGroup()

            if(result >= 1)
            {
                Toast.makeText(this, "Group Added", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Operation to create a group
     */
    fun CreateGroup() : Int
    {
        var result : Int = 0

        val nameTextView = findViewById(R.id.nameText) as TextView
        val name : String = nameTextView.text.toString()

        var DB:DatabaseHandler = DatabaseHandler(this)

        result = DB.CreateGroup(MyGroup(0, name))

        return result
    }

    /**
     * Operation to navigate to the MainActivity Screen/Activity
     */
    fun AccessMainActivity()
    {
        var intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
