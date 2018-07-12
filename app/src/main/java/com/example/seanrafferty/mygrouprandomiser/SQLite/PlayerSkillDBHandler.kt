package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill

class PlayerSkillDBHandler
{
    val _DB : DatabaseHandler

    /**
     * constructor for player DB handler
     */
    constructor(db:DatabaseHandler)
    {
        _DB = db
    }

    /**
     * Operation to request all of the available player skill options
     * @return an arraylist of player skills
     */
    fun GetAllPlayerSkills() : ArrayList<PlayerSkill>
    {
        val selectQuery = "SELECT * FROM ${DatabaseHandler.PlayerSkillTable}"
        val db = _DB.GetReadableDataBaseObject()

        var cursor: Cursor? = null
        cursor = try {
            db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            cursor!!.close()
            return null!!
        }

        //initialise the arrayList
        var arrayList = arrayListOf<PlayerSkill>()
        if(cursor != null)
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.PlayerSkillpkID))
                    val name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PlayerSkillName))
                    val modifier = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.PlayerSkillModifier))

                    arrayList.add(PlayerSkill(id, name, modifier))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList
    }

    /**
     * Operation to handle the insertion of an individual player skill to the datastore
     * @param skill : the player skill to parse and insert
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun InsertPlayerSkill(skill : PlayerSkill) : Int
    {
        var values = ContentValues()
        values.put(DatabaseHandler.PlayerSkillName, skill.name)
        values.put(DatabaseHandler.PlayerSkillModifier, skill.modifier)

        var result = 0
        val db = _DB.GetWritableDataBaseObject()
        result = try
        {
            db!!.insertOrThrow(DatabaseHandler.groupTableName, "", values).toInt()
        }
        catch (e: SQLiteException)
        {
            Log.e("EXCEPTION", " ${object{}.javaClass.enclosingMethod.name} query failed with message : ${e.message}")
            -1
        }
        return result
    }
}