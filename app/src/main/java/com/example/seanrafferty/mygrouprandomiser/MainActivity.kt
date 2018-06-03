package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.seanrafferty.mygrouprandomiser.Adapters.GroupAdapter
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.InitialiseDataDBHandler
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.android.synthetic.main.activity_navigation_bar.*
import kotlinx.android.synthetic.main.app_bar_navigation_bar.*
import com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount
import android.widget.TextView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    //List of groups to be displayed within a list view
    private lateinit var _GroupListView : ListView
    private lateinit var GoogleUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Get Google Profile information
        //Handle google account info from user login credentials
        //wonder if this could be added to an account manager
        val acct = getLastSignedInAccount(this@MainActivity)
        if (acct != null)
        {
            //we might not need this then - or handle the user details into the system
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto = acct.photoUrl

            UpdateNavigationBarDetails(acct)
        }

        GoogleUserName = findViewById(R.id.textView2)
        GoogleUserName.text = acct?.displayName
        
        _GroupListView = findViewById(R.id.GroupListView)

        // request all stored MyGroup objects and append these to a listview adapter
        var groupList = RequestGroups()
        var groupAdapter = GroupAdapter(this, groupList)
        _GroupListView.adapter = groupAdapter

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
     * handle creation of option action overflow on view (3 vertical dots)
     * with xml menu option file targeted
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
        return true
    }

    /**
     * Handle updating navigation drawer user content
     */
    private fun UpdateNavigationBarDetails(acct : GoogleSignInAccount)
    {
        Log.d("TAG ", object{}.javaClass.enclosingMethod.name)
        if (acct != null)
        {
            val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
            val headerView = navigationView.getHeaderView(0)

            var txtNavHeader = headerView.findViewById<TextView>(R.id.textViewNavDrawerEmail)
            txtNavHeader.text = acct.email
        }
    }

    /**
     * Request all stored groups available
     * @returns An arraylist of MyGroup objects
     */
    private fun RequestGroups() : ArrayList<MyGroup>
    {
        Log.d("TAG ", object{}.javaClass.enclosingMethod.name)
        val groupManager = MyGroupManager(this)

        //Request all groups from the database and return the data
        return groupManager.ReadAllGroups()
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
                Toast.makeText(this, "Resetting Database", Toast.LENGTH_LONG).show()
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


