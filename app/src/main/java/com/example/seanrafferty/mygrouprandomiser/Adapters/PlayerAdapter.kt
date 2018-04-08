package com.example.seanrafferty.mygrouprandomiser.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.R
import kotlinx.android.synthetic.main.group_listview_item.view.*
import kotlinx.android.synthetic.main.player_listview_item.view.*

class PlayerAdapter : BaseAdapter
{
    var arrayList = ArrayList<Player>()
    var context: Context? = null
    var myInflater: LayoutInflater? = null

    /**
     * object constructor
     */
    constructor(con: Context, arrList: ArrayList<Player>) : super()
    {
        //println("PlayerAdapter: Constructor")
        this.context = con;
        this.arrayList = arrList
        this.myInflater = LayoutInflater.from(context)
    }

    /**
     * overridden method to get the current list item view
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        if(convertView == null)
        {
            view = myInflater!!.inflate(R.layout.player_listview_item, null)
        }
        else
        {
            view = convertView
        }

        var playerObj = arrayList[position]

        //Add single player content to the available text views
        view.playerNameView.text = playerObj.Name
        view.playerRatingView.text = playerObj.Rating.toString()


        return view
    }

    /**
     * Return the player object at the provided position
     */
    override fun getItem(position: Int): Any
    {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

}