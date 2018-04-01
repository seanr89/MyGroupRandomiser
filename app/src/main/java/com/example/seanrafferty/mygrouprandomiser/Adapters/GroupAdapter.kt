package com.example.seanrafferty.mygrouprandomiser.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.security.acl.Group

class GroupAdapter : BaseAdapter
{
    var arrayList = ArrayList<Group>()
    var context: Context? = null
    var myInflater: LayoutInflater? = null

    constructor(con: Context, arrList:ArrayList<Group>) : super()
    {
        this.context = con;
        this.arrayList = arrList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}