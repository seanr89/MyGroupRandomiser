package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.util.Log
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
import kotlinx.android.synthetic.main.app_bar_navigation_bar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    //List of groups to be displayed within a list view
    private lateinit var _GroupListView : ListView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        _GroupListView = findViewById(R.id.GroupListView)

        // request all stored MyGroup objects and append these to a listview adapter
        var groupList = RequestGroups()
        var groupAdapter = GroupAdapter(this, groupList)
        _GroupListView.adapter = groupAdapter;

        //Handle listview item selection for a group
        _GroupListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            // Handle group item selection and ensure object casting
            var selectedGroup = _GroupListView.adapter.getItem(position) as MyGroup
            //now navigate to the group info activity
            NavigationControls.NavigateToGroupInfoActivity(this, selectedGroup.ID)
        }
        //initialise the navigation app drawer to be loaded in
        nav_view.setNavigationItemSelectedListener(this)
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
    private fun RequestGroups() : ArrayList<MyGroup>
    {
        Log.d("TAG ", object{}.javaClass.enclosingMethod.name)
        //initialise an ArrayList and a DatabaseHandler object
        val groupList: ArrayList<MyGroup>
        val groupDB = MyGroupDBHandler(DatabaseHandler(this))

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
     * Handle refreshing of listview adapter datasource
     */
    private fun RefreshListView()
    {
        val data = RequestGroups()
        val adapter = _GroupListView.adapter as GroupAdapter
        adapter.arrayList = data
        adapter.notifyDataSetChanged()
    }

    /**
     * Handle item selection events for navigation drawer item selection
     * @param item : handle the selection of the menu items
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
            R.id.nav_addGroup -> {
                NavigationControls.NavigateToEditGroupActivity(this)
            }
            R.id.nav_init_db -> {
                Toast.makeText(this, "Settings not available", Toast.LENGTH_LONG).show()
                var DBReset = DatabaseHandler(this)
                DBReset.onUpgrade(DBReset.writableDatabase, DatabaseHandler.DBVersion , DatabaseHandler.DBVersion)
                var dbInitData = InitialiseDataDBHandler(DatabaseHandler(this))
                dbInitData.RunDataInitialisation()

                RefreshListView()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}


