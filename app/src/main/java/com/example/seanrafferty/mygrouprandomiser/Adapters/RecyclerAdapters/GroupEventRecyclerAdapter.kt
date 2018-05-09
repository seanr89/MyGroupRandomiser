package com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seanrafferty.mygrouprandomiser.Models.GroupEvent
import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.Utilities.SelectionOption

class GroupEventRecyclerAdapter(var eventList: ArrayList<GroupEvent>,
                                var selectionOption: SelectionOption = SelectionOption.NO_SELECT) : RecyclerView.Adapter<GroupEventRecyclerAdapter.EventViewHolder>()
{

    lateinit var SelectedItems : ArrayList<GroupEvent>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupEventRecyclerAdapter.EventViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.player_listview_item, parent, false)
        return EventViewHolder(v)
    }

    override fun getItemCount(): Int
    {
        return eventList.count()
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        if(isItemSelected(position))
        {
            ColourItemSelected(holder.itemView)
        }
        else
        {
            ColourItemDeSelected(holder.itemView)
        }

        when(selectionOption)
        {
            SelectionOption.NO_SELECT ->
            {
                //Do Nothing!!
            }
            SelectionOption.SINGLE_SELECT ->
            {
                holder.itemView.setOnClickListener()
                {
                    SingleItemSelected(holder.itemView, position)
                }
            }
            SelectionOption.MULTI_SELECT ->
            {
                holder.itemView.setOnClickListener()
                {
                    MultiItemSelected(holder.itemView, position)
                }
            }
        }
    }


    private fun SingleItemSelected(view:View, position: Int)
    {
        Log.d("GroupEventRecyclerAdapter", object{}.javaClass.enclosingMethod.name)
        if(!SelectedItems.isEmpty())
        {
            SelectedItems.clear()
            this.notifyItemChanged(position)
        }
        SelectedItems.add(eventList[position])
        ColourItemSelected(view)
    }

    private fun MultiItemSelected(view:View, position: Int)
    {
        Log.d("GroupEventRecyclerAdapter", object{}.javaClass.enclosingMethod.name)
        //Log.d("PlayerRecyclerAdapter", object{}.javaClass.enclosingMethod.name + " with position $position")
        if(isItemSelected(position))
        {
            SelectedItems.remove(eventList[position])
            //var test = view.itemView
            ColourItemDeSelected(view)
        }
        else
        {
            SelectedItems.add(eventList[position])
            ColourItemSelected(view)
        }

    }

    /**
     * Set the colour of the item view in the recycler to highlight selected
     */
    private fun ColourItemSelected(view:View)
    {
        view.setBackgroundColor(Color.LTGRAY)
    }

    /**
     * Set the colour of the item view in the recycler to highlight de-selected
     */
    private fun ColourItemDeSelected(view:View)
    {
        view.setBackgroundColor(Color.WHITE)
    }


    /**
     * check if the selected item has been selected or not
     * @param position : the position of the item on the click event
     * @return boolean : true if the item is in the selected list
     */
    private fun isItemSelected(position: Int) : Boolean
    {
        var result = false

        if(!SelectedItems.filter { it.ID == SelectedItems[position].ID }.isEmpty())
        {
            result = true
        }
        return result
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