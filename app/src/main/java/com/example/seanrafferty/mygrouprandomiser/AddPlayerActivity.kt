package com.example.seanrafferty.mygrouprandomiser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_player.*


class AddPlayerActivity : AppCompatActivity() {

    lateinit var _RatingSpinner : Spinner
    lateinit var _SavePlayerBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)

        val ratings = Array(10, { i -> (i + 1) })

        _RatingSpinner = findViewById(R.id.spinnerRating)
        _SavePlayerBtn = findViewById(R.id.btnAddGroup)

        val spinnerAdapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, ratings)

        _RatingSpinner.adapter = spinnerAdapter;

        _RatingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        _SavePlayerBtn.setOnClickListener()
        {
            SaveNewPlayer()

//            if(result >= 1)
//            {
//                Toast.makeText(this, "Group Added", Toast.LENGTH_LONG).show()
//            }
        }
    }

    /**
     * Read player details and save content to the internal DB
     */
    fun SaveNewPlayer()
    {

    }

    /**
     * Navigate the Player Activity screen
     */
    fun NavigateToPlayerActivity()
    {
        var intent= Intent(this,PlayerActivity::class.java)
        startActivity(intent)
    }
}
