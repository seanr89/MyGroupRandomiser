package com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.Utilities.SelectionOption

/**
 * Recycler Adapter to handle display controls for player skill objects
 */
class PlayerSkillRecyclerAdapter<T>(var itemList: ArrayList<PlayerSkill>,
                                 var selectionOption: SelectionOption = SelectionOption.NO_SELECT) : RecyclerView.Adapter<PlayerSkillRecyclerAdapter.ViewHolder>()
{
    var SelectedItems : ArrayList<PlayerSkill> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.playerskill_listview_item, parent, false)
        return ViewHolder(v, selectionOption)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName?.text = itemList[position].name
        holder?.txtModifier?.text = itemList[position].modifier.toString()

        //Check if the item is selected
        if(holder?.itemSelectable)
        {
            //if the current item is selected
            if(isItemSelected(position))
            {
                SelectedItems.remove(itemList[position])
                //var test = view.itemView
                ColourItemDeSelected(holder.itemView)
            }
            else
            {
                SelectedItems.add(itemList[position])
                ColourItemSelected(holder.itemView)
            }

            //Enable button click event for handling item clicks
            holder.itemView.setOnClickListener {
                SetItemSelected(position, holder.itemView)
            }
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
     * Set the selected recycler view item as selected
     * @param position : the position of the item in the view and the provided array list
     * @param view : the individual view within the recycler
     */
    private fun SetItemSelected(position: Int, view:View)
    {
        if(isItemSelected(position))
        {
            SelectedItems.remove(itemList[position])
            //var test = view.itemView
            ColourItemDeSelected(view)
        }
        else
        {
            SelectedItems.add(itemList[position])
            ColourItemSelected(view)
        }
    }

    /**
     * check if the selected item has been selected or not
     * @param position : the position of the item on the click event
     * @return boolean : true if the item is in the selected list
     */
    private fun isItemSelected(position: Int) : Boolean
    {
        var result = false

        if(!SelectedItems.filter { it.id == itemList[position].id }.isEmpty())
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
    class ViewHolder : RecyclerView.ViewHolder
    {
        var itemSelectable : Boolean = false
        var txtName : TextView
        var txtModifier : TextView

        constructor(itemView: View, selectable : SelectionOption) : super(itemView)
        {
            itemSelectable = isItemSelectable(selectable)
            txtName = itemView.findViewById(R.id.txtViewSkillName)
            txtModifier = itemView.findViewById(R.id.txtViewSkillModifier)
        }

        private fun isItemSelectable(selection: SelectionOption) : Boolean
        {
            var result = false
            when(selection)
            {
                SelectionOption.SINGLE_SELECT -> result = true
                SelectionOption.MULTI_SELECT -> result = true
            }
            return result
        }
    }
}