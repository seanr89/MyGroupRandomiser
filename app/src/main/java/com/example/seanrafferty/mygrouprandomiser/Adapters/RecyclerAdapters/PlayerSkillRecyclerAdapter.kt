package com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.Utilities.SelectionOption

/**
 * Recycler Adapter to handle display controls for player skill objects
 */
class PlayerSkillRecyclerAdapter<T>(var itemList: ArrayList<PlayerSkill>,
                                 var selectionOption: SelectionOption = SelectionOption.NO_SELECT) : RecyclerView.Adapter<PlayerSkillRecyclerAdapter.ViewHolder>()
{
    var SelectedItems  : ArrayList<T> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.playerskill_listview_item, parent, false)
        return ViewHolder(v, selectionOption)
    }

    override fun getItemCount(): Int {
        return itemList.size
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

        constructor(itemView: View, selectable : SelectionOption) : super(itemView)
        {
            //itemSelectable = selectable
            //txtName = itemView.findViewById(R.id.txtPlayerName)
            //txtRating = itemView.findViewById(R.id.txtPlayerRating)
        }
    }
}