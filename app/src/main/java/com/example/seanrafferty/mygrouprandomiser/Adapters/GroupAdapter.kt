package com.example.seanrafferty.mygrouprandomiser.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.R
import com.example.seanrafferty.mygrouprandomiser.Utilities.SelectionOption
import kotlinx.android.synthetic.main.group_listview_item.view.*

class GroupAdapter : BaseAdapter
{
    var arrayList = ArrayList<MyGroup>()
    var myInflater: LayoutInflater? = null

    private var selectionOption : SelectionOption

    /**
     * Constructor
     */
    constructor(con: Context, arrList: ArrayList<MyGroup>, selectionOption: SelectionOption = SelectionOption.NO_SELECT) : super()
    {
        //println("GroupAdapter: Constructor")
        this.arrayList = arrList
        this.myInflater = LayoutInflater.from(con)
        this.selectionOption = selectionOption
    }

    /**
     * Initialise and populate each individual view with group info
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        //println("PlayerAdapter: getView called")
        val view: View = convertView ?: myInflater!!.inflate(R.layout.group_listview_item, null)

        var groupObj = arrayList[position]

        view.LeftTextView.text = groupObj.Name

        return view
    }

    /**
     * get the group item at the selected position
     */
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