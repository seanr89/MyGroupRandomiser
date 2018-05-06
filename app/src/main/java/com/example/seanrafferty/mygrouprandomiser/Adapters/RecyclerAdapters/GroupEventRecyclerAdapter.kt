package com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.R

class GroupEventRecyclerAdapter(var eventList: ArrayList<GroupEvent>) : RecyclerView.Adapter<GroupEventRecyclerAdapter.EventViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupEventRecyclerAdapter.EventViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.player_listview_item, parent, false)
        return EventViewHolder(v)
    }

    override fun getItemCount(): Int
    {
        return eventList.count()
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    ////////////////////////////////////////////////////////////////////////////////
    /**
     * Custom class for handling a singe recycler view item
     */
    ////////////////////////////////////////////////////////////////////////////////


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class EventViewHolder : RecyclerView.ViewHolder
    {
        constructor(itemView: View) : super(itemView)
    }
}