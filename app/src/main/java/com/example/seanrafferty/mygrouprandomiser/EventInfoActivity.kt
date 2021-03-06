package com.example.seanrafferty.mygrouprandomiser

import android.net.Uri
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.seanrafferty.mygrouprandomiser.Business.EventManager
import com.example.seanrafferty.mygrouprandomiser.Fragments.EventInfoFragment
import com.example.seanrafferty.mygrouprandomiser.Fragments.SingleTeamFragment
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import kotlinx.android.synthetic.main.activity_event_info.*

class EventInfoActivity : AppCompatActivity(), EventInfoFragment.OnFragmentInteractionListener
{
    override fun onFragmentInteraction(uri: Uri)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var Event : GroupEvent
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
        setContentView(R.layout.activity_event_info)

        /*setSupportActionBar(toolbar)*/

        var eventManager = EventManager(this)
        Event = eventManager.GetEventByID(intent.getStringExtra("EventID").toInt())

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.offscreenPageLimit = 2 //initialise off page limit to 2 to allow both teams be maintained in memory
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_event_info, menu)
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
     * tab1: EventInfo
     * tab2: Team1 SingleTeamFragment
     * tab3: Team2 SingleTeamFragment
     */
    inner class SectionsPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            when(position)
            {
                0 -> return EventInfoFragment.newInstance(Event)
                1 -> return SingleTeamFragment.newInstance(Event.EventTeams[0], "Team 1")
                2 -> return SingleTeamFragment.newInstance(Event.EventTeams[1], "Team 2")
            }
            return null!!
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }
}
