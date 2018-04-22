package com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.R
import org.w3c.dom.Text

class PlayerRecyclerAdapter(var playerList: ArrayList<Player>, val selectable : Boolean = false) : RecyclerView.Adapter<PlayerRecyclerAdapter.ViewHolder>()
{
    var SelectedItems : ArrayList<Player> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.player_listview_item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder?.txtName?.text = playerList[position].Name
        holder?.txtRating?.text = playerList[position].Rating.toString()

        //if each item is selectable
        if(selectable)
        {
            //Append on click listener to handle item selection
            holder.itemView.setOnClickListener()
            {
                SetItemSelected(position, holder.itemView)
            }
        }
    }

    /**
     * Set the selected recycler view item as selected
     */
    private fun SetItemSelected(position: Int, view:View)
    {
        Log.d("PlayerRecyclerAdapter", object{}.javaClass.enclosingMethod.name)
        if(isItemSelected(position))
        {
            SelectedItems.remove(playerList[position])
            view.setBackgroundColor(Color.GRAY)
        }
        else
        {
            SelectedItems.add(playerList[position])
            view.setBackgroundColor(Color.RED)
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

        if(!SelectedItems.filter { it.ID == playerList[position].ID }.isEmpty())
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
        var txtName : TextView
        var txtRating : TextView
        constructor(itemView: View) : super(itemView)
        {
            txtName = itemView.findViewById<TextView>(R.id.playerNameView)
            txtRating = itemView.findViewById<TextView>(R.id.playerRatingView)
        }
    }
}