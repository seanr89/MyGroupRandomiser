package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.acl.Group

class DatabaseHandler : SQLiteOpenHelper
{

    companion object {
        //val Tag = "DatabaseHandler"
        val DBName = "GroupDB"
        val DBVersion = 1

        val groupTableName = "Group"
        val grouppkID = "ID"
        val groupName = "Name"
    }


    var context: Context? = null
    var sqlObj: SQLiteDatabase

    constructor(context: Context) : super(context, DBName, null, DBVersion) {
        this.context = context
        sqlObj = this.writableDatabase;
    }

    /**
     * Handle on DB creation - this is called each time the DB handler is instantiated
     */
    override fun onCreate(db: SQLiteDatabase?)
    {
        //db!!.execSQL(CREATE_TABLE_SQL)
        //Create the Group Table if it doesn't already exist
        var sqlCreateGroup: String =  "CREATE TABLE IF NOT EXISTS " + groupTableName + " " +
            "(" + grouppkID + " INTEGER PRIMARY KEY," +
            groupName + " TEXT, " + ");"

        db!!.execSQL(sqlCreateGroup);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("Drop table IF EXISTS " + groupTableName)
        onCreate(db)


    }


    ////////////////////////////////////////////////////////////////////////////////
    /**
     * GROUP CRUD EVENTS
     */

    /**
     *
     */
    fun ReadAllGroups() : ArrayList<Group>
    {
        var arrayList = ArrayList<Group>();

        return arrayList;
    }

    /**
     *
     */
    @Throws(SQLiteConstraintException::class)
    fun CreateGroup(group: Group) : Int
    {
        var result : Int = 0;

        var values = ContentValues()
        values.put("Name", group.name)

        result = sqlObj!!.insert(groupTableName, "", values).toInt()

        return result;
    }
}