package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Adapters.GroupAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.InitialiseDataDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.MyGroupDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import kotlinx.android.synthetic.main.activity_navigation_bar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    lateinit var _GroupListView : ListView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _GroupListView = findViewById(R.id.GroupListView) as ListView

        // Handler code here. - request all stored MyGroup objects and append these to a listview adapter
        var groupList = RequestGroups()
        var groupAdapter = GroupAdapter(this, groupList)
        _GroupListView.adapter = groupAdapter;

        var btn_add_group = findViewById<Button>(R.id.btnAddGroup)
        btn_add_group.setOnClickListener()
        {
            NavigationControls.NavigateToEditGroupActivity(this)
        }

        //Handle listview item selection for a group
        _GroupListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            // Handle group item selection and ensure object casting
            var selectedGroup = _GroupListView.adapter.getItem(position) as MyGroup
            //Log.d("ListView Selected","${selectedGroup.toString()} selected")
            NavigationControls.NavigateToGroupInfoActivity(this, selectedGroup.ID)
        }
    }

    /**
     * handle creation of option action overflow on view
     * with xml menu option file targetted
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
       // menuInflater.inflate(R.menu.action_overflow, menu)
        return true
    }

    /**
     * overridden handle action overflow item selection events
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
//            when(item.itemId)
//            {
//                R.id.action_players -> {
//                    NavigationControls.NavigateToPlayerActivity(this)
//                    return true
//                }
//                R.id.action_settings -> {
//                    Toast.makeText(this, "Settings not available", Toast.LENGTH_LONG).show()
//                    var DBReset = DatabaseHandler(this)
//                    DBReset.onUpgrade(DBReset.writableDatabase, 9 , 9)
//                    var dbInitData = InitialiseDataDBHandler(DatabaseHandler(this))
//                    dbInitData.RunDataInitialisation()
//                }
//            }
        return true
    }

    /**
     * Request all stored groups available
     * @returns An arraylist of MyGroup objects
     */
    fun RequestGroups() : ArrayList<MyGroup>
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)
        //initialise an ArrayList and a DatabaseHandler object
        var groupList: ArrayList<MyGroup>
        var groupDB = MyGroupDBHandler(DatabaseHandler(this))

        //Request all groups from the database and return the data
        groupList = groupDB.ReadAllGroups()
        return groupList
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Handle refreshing of listview adapater datasource
     */
    private fun RefreshListView()
    {
        var data = RequestGroups()
        var adapter = _GroupListView.adapter as GroupAdapter
        adapter.arrayList = data
        adapter.notifyDataSetChanged()
    }

    /**
     * Handle item selection events for navigation drawer item selection
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_manage -> {
                Toast.makeText(this, "Not Yet Available!!", Toast.LENGTH_LONG).show()
            }
            R.id.nav_players -> {
                NavigationControls.NavigateToPlayerActivity(this)
            }
            R.id.nav_init_db -> {
                Toast.makeText(this, "Settings not available", Toast.LENGTH_LONG).show()
                var DBReset = DatabaseHandler(this)
                DBReset.onUpgrade(DBReset.writableDatabase, 11 , 11)
                var dbInitData = InitialiseDataDBHandler(DatabaseHandler(this))
                dbInitData.RunDataInitialisation()

                RefreshListView()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}


