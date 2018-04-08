package com.example.seanrafferty.mygrouprandomiser.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.R
import kotlinx.android.synthetic.main.group_listview_item.view.*

class GroupAdapter : BaseAdapter
{
    var arrayList = ArrayList<MyGroup>()
    var context: Context? = null
    var myInflater: LayoutInflater? = null

    /**
     * Constructor
     */
    constructor(con: Context, arrList: ArrayList<MyGroup>) : super()
    {
        println("GroupAdapter: Constructor")
        this.context = con;
        this.arrayList = arrList
        this.myInflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        //println("PlayerAdapter: getView called")
        val view: View

        if(convertView == null)
        {
            view = myInflater!!.inflate(R.layout.group_listview_item, null)
        }
        else
        {
            view = convertView
        }

        var groupObj = arrayList[position]

        view.LeftTextView.text = groupObj.Name

        return view
    }

    override fun getItem(position: Int): MyGroup {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}