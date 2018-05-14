package com.example.seanrafferty.mygrouprandomiser.Adapters.RecyclerAdapters

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Customer view holder class to be used by RecyclerViews long terms to provide selection option controls
 */
class SelectableViewHolder : RecyclerView.ViewHolder {

    constructor(itemView: View) : super(itemView)

    /**
     * Initialisation block used when constructor initialisation cannot
     */
    init
    {
        itemView.setOnClickListener { v: View  ->

        }
    }
}