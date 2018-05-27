package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Fragments.EventSetupFragment
import com.example.seanrafferty.mygrouprandomiser.Fragments.TeamsFragment
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.Models.Team
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import kotlinx.android.synthetic.main.activity_group_event_generator.*

class GroupEventGeneratorActivity : AppCompatActivity(),
        EventSetupFragment.OnRandomTeamsGenerated,
        TeamsFragment.OnFragmentInteractionListener
{
    private var _GroupID : Int = 0
    private lateinit var Teams : ArrayList<Team>


    /**
     * Handle the interaction of the event setup fragment
     * @param teams : the array list of teams generated
     */
    override fun onFragmentInteraction(teams : ArrayList<Team>)
    {
        var groupEvent = CreateEventGroupFromContent()
        if(groupEvent != null)
        {
            var eventManager = EventManager(this)
            eventManager.SaveEvent(groupEvent)
        }
        Toast.makeText(this, "Event Scheduled", Toast.LENGTH_LONG).show()
        NavigationControls.NavigateToGroupInfoActivity(this, _GroupID)
    }

    /**
     * Handle event from setup fragment noting the completion team randomisation
     * @param teams : handle the randomization of teams with update the respective fragments
     */
    override fun onTeamsRandomized(teams: ArrayList<Team>)
    {
        Log.d("GroupEventGeneratorAct", object{}.javaClass.enclosingMethod.name)
        UpdateTeamsFragmentsWithRandomizedPlayers(teams)
    }

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
        //setSupportActionBar(toolbar)

        Teams = arrayListOf()
        _GroupID = intent.getStringExtra("GroupID").toInt()

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mSectionsPagerAdapter!!.getItem(2)

        // Set up the ViewPager with the sections adapter.
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
        //Log.d("GroupEventGeneratorAct", object{}.javaClass.enclosingMethod.name)
        layout.getTabAt(0)!!.text = "Event Setup"
        layout.getTabAt(1)!!.text = "Teams"
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
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment
        {
            Log.d(object{}.javaClass.enclosingMethod.name, " fragment position : $position")
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            var fragment = Fragment()

            when(position)
            {
                0 -> fragment = EventSetupFragment.newInstance(_GroupID)
                1 -> fragment = TeamsFragment.newInstance(Teams, 1, "Team1")
                else -> { // Note the block
                    print("x is neither 0, 1 nor 2")
                }
            }
            return fragment
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }
    }

    /**
     * trigger the updating of the teams fragment with the players that have been randomized
     * @param teams : the two auto generated teams with random players
     */
    fun UpdateTeamsFragmentsWithRandomizedPlayers(teams:ArrayList<Team>)
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)
        Teams = teams
        var fragment = supportFragmentManager.fragments[1] as TeamsFragment
        fragment.UpdateRecyclerAdapter(Teams)
    }

    /**
     * Create the group event object from the provided content in the activity
     * @return a created GroupEvent object
     */
    private fun CreateEventGroupFromContent() : GroupEvent
    {
        Log.d("TAG", object{}.javaClass.enclosingMethod.name)

        //get the setupFragment
        var eventFragment = supportFragmentManager.fragments[0] as EventSetupFragment
        var dateTime = eventFragment.GetSelectedDateAndTime()

        return GroupEvent(0, dateTime, _GroupID, false, Teams)
    }
}
