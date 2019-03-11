package com.example.seanrafferty.mygrouprandomiser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Fragments.EventSetupFragment
import com.example.seanrafferty.mygrouprandomiser.Fragments.ShuffleUpDialog
import com.example.seanrafferty.mygrouprandomiser.Fragments.SingleTeamFragment
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import kotlinx.android.synthetic.main.activity_group_event_generator.*

class GroupEventGeneratorActivity : AppCompatActivity(),
        EventSetupFragment.OnRandomTeamsGenerated,
        EventSetupFragment.OnSaveEvent,
        ShuffleUpDialog.RandomisationSelectedListener
{
    // These need to be handled/reworded
    private var _GroupID : Int = 0
    private lateinit var Teams : ArrayList<Team>

    private val TAG = "GroupEventGeneratorAct"

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_event_generator)

        Teams = arrayListOf()
        _GroupID = intent.getStringExtra("GroupID").toInt()

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mSectionsPagerAdapter!!.getItem(2)

        // Set up the ViewPager with the sections adapter.
        container.offscreenPageLimit = 2 //initialise off page limit to 2
        container.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(container)

        configureTabLayoutTitles(tabLayout)
    }

    /**
     * Configure the titles to be displayed for each fragment in the tab layout
     */
    private fun configureTabLayoutTitles(layout : TabLayout)
    {
        layout.getTabAt(0)!!.text = "Setup"
        layout.getTabAt(1)!!.text = "Team 1"
        layout.getTabAt(2)!!.text = "Team 2"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_group_event_generator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    /**
     * Test method to handle fragment navigation on click event
     */
    fun NavigateToFirstTeam()
    {
        container.setCurrentItem(1, true)
    }


    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    inner class SectionsPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            var fragment = androidx.fragment.app.Fragment()

            when(position)
            {
                0 -> fragment = EventSetupFragment.newInstance(_GroupID)
                1 -> fragment = SingleTeamFragment.newInstance(Team(0, "Team 1", 0), "Team1")
                2 -> fragment = SingleTeamFragment.newInstance(Team(0, "Team 2", 0), "Team2")
                else -> { // Note the block
                    print("x is neither 0, or 1")
                }
            }
            return fragment
        }

        override fun getCount(): Int {
            // Show X total pages.
            return 3
        }
    }

    /**
     * trigger the updating of the teams fragment with the players that have been randomized
     * @param teams : the two auto generated teams with random players
     */
    fun UpdateTeamsFragmentsWithRandomizedPlayers(teams:ArrayList<Team>)
    {
        Teams = teams

        //same here
        val teamOneFragment = supportFragmentManager.fragments[1] as SingleTeamFragment
        teamOneFragment.UpdateTeamPlayers(Teams[0])

        //may want to add in a try catch around this!!
        val teamTwoFragment = supportFragmentManager.fragments[2] as SingleTeamFragment
        teamTwoFragment.UpdateTeamPlayers(Teams[1])

        NavigateToFirstTeam()
    }

    /**
     * Create the group event object from the provided content in the activity
     * Also includes the addition of the teams to the event
     * @return a created GroupEvent object
     */
    private fun CreateEventGroupFromContent() : GroupEvent
    {
        //get the setupFragment and request the current selected date and time of the event
        val eventFragment = supportFragmentManager.fragments[0] as EventSetupFragment
        val dateTime = eventFragment.GetSelectedDateAndTime()
        return GroupEvent(0, dateTime, _GroupID, false, false, Teams)
    }

    ///////***************************************************************////////
    ///////***************************************************************////////
    ///////***************************************************************////////

    /**
     * Handle event from setup fragment noting the completion team randomisation
     * @param teams : handle the randomization of teams with update the respective fragments
     */
    override fun onTeamsRandomized(teams: ArrayList<Team>)
    {
        UpdateTeamsFragmentsWithRandomizedPlayers(teams)
    }

    /**
     * Handle save request for the entire event (teams included)
     */
    override fun saveEvent()
    {
        //initialise the group event and request event data from the setup fragment
        var groupEvent = CreateEventGroupFromContent()
        if(groupEvent != null)
        {
            //initialise the event manager and start the saving process
            var eventManager = EventManager(this)
            eventManager.SaveEvent(groupEvent)
        }
        Toast.makeText(this, "Event Scheduled", Toast.LENGTH_LONG).show()
        NavigationControls.NavigateToGroupInfoActivity(this, _GroupID)
    }


    ///////***************************************************************////////
    ///////***************************************************************////////
    ///////***************************************************************////////


    override fun shufflePlayersRandomly() {
        val eventFragment = supportFragmentManager.fragments[0] as EventSetupFragment
        eventFragment.CreateRandomTeams()
    }

    override fun shufflePlayersByRating() {
        val eventFragment = supportFragmentManager.fragments[0] as EventSetupFragment
        eventFragment.CreateTeamsByRatingAndTriggerEvent()
    }

    override fun shufflePlayersByRatingAndSkill()
    {
        val eventFragment = supportFragmentManager.fragments[0] as EventSetupFragment
        eventFragment.CreateTeamsByRatingSkillsAndTriggerEvent()
    }
}
