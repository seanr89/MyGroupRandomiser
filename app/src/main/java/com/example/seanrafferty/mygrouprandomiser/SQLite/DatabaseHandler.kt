package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup

class DatabaseHandler : SQLiteOpenHelper
{

    companion object {
        //val Tag = "DatabaseHandler"
        val DBName = "GroupDB.db"
        val DBVersion = 6

        val groupTableName = "mygroup"
        val grouppkID = "ID"
        val groupName = "Name"
        val groupDate = "CreationDate"
    }

    var context: Context? = null

    constructor(context: Context) : super(context, DBName, null, DBVersion) {
        this.context = context
    }

    /**
     * Handle on DB creation - this is called each time the DB handler is instantiated
     */
    override fun onCreate(db: SQLiteDatabase)
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)

        //Create the Group Table if it doesn't already exist
        var sqlCreateGroup: String =  "CREATE TABLE IF NOT EXISTS ${groupTableName} " +
            "(${grouppkID} INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "${groupName} TEXT, " +
            "${groupDate} TEXT);"

        if(db != null)
        {
            db.execSQL(sqlCreateGroup)
        }
        else{
            println("database is null")
        }
    }

    /**
     * Overridden function from SQLOpenHelper to upgrade the function
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        println("Method: " + object{}.javaClass.enclosingMethod.name)
        var sqlDeleteGroup: String = "DROP TABLE IF EXISTS '$groupTableName'"
        db.execSQL(sqlDeleteGroup)
        onCreate(db)
    }


    ////////////////////////////////////////////////////////////////////////////////
    /**
     * GROUP CRUD EVENTS
     */
    ////////////////////////////////////////////////////////////////////////////////

    /**
     *
     */
    fun ReadAllGroups() : ArrayList<MyGroup> {
        println("Method: " + object {}.javaClass.enclosingMethod.name)

        var arrayList = ArrayList<MyGroup>()

        // Select All Query
        var selectQuery: String = "SELECT * FROM '$groupTableName'"
        val db = this.readableDatabase;

        var cursor = db!!.rawQuery(selectQuery, null)

        if (cursor == null)
        {
            println("cursor is null")
        }
        else
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(grouppkID))
                    val name = cursor.getString(cursor.getColumnIndex(groupName))

                    arrayList.add(MyGroup(id, name))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList;
    }

    /**
     *
     */
    @Throws(SQLiteConstraintException::class)
    fun CreateGroup(group: MyGroup) : Int
    {
        var result : Int = 0;

        var values = ContentValues()
        values.put("Name", group.Name)

        val db = this.writableDatabase

        result = db!!.insert(groupTableName, "", values).toInt()

        return result;
    }
}