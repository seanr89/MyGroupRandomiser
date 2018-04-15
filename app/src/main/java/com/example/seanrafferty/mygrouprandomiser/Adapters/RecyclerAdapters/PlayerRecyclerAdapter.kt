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

class PlayerRecyclerAdapter(val playerList: ArrayList<Player>) : RecyclerView.Adapter<PlayerRecyclerAdapter.ViewHolder>()
{
    var SelectedPlayer : ArrayList<Player> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.player_listview_item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName?.text = playerList[position].Name
        holder?.txtRating?.text = playerList[position].Rating.toString()

        holder.itemView.setOnClickListener()
        {
            SetItemSelected(position, holder.itemView)
        }
    }

    private fun SetItemSelected(position: Int, view:View)
    {
        Log.d("PlayerRecyclerAdapter", object{}.javaClass.enclosingMethod.name)

        var selectedItem = playerList[position]

        view.setBackgroundColor(Color.RED)


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
        lateinit var txtName : TextView
        lateinit var txtRating : TextView
        constructor(itemView: View) : super(itemView)
        {
            val txtName = itemView.findViewById<TextView>(R.id.playerNameView)
            val txtRating = itemView.findViewById<TextView>(R.id.playerRatingView)
        }
    }
}