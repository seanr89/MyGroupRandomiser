package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player

/**
 * Provide business logic and layer to communicate between presentation and data layers
 * @param context : Activity Context to allow DBHelper integration
 */
class MyGroupManager(val context: Context)
{

    /**
     * New method to move events to business layer from datalayer
     */
    fun ReadAllPlayerForGroup(roup: MyGroup) : ArrayList<Player>
    {
        return null!!
    }
}