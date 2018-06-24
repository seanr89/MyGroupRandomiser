package com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.R

/**
 * Recycler Adapter to handle display controls for player skill objects
 */
class PlayerSkillRecyclerAdapter() : RecyclerView.Adapter<PlayerSkillRecyclerAdapter.ViewHolder>()
{
    //var SelectedItems = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
    class ViewHolder : RecyclerView.ViewHolder
    {
        var itemSelectable : Boolean = false

        constructor(itemView: View, selectable: Boolean) : super(itemView)
        {
            itemSelectable = selectable
            //txtName = itemView.findViewById(R.id.txtPlayerName)
            //txtRating = itemView.findViewById(R.id.txtPlayerRating)
        }
    }
}