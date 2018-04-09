package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player

class DatabaseHandler : SQLiteOpenHelper
{

    companion object {
        val DBName = "GroupDB.db"
        val DBVersion = 8

        const val groupTableName = "mygroup"
        const val grouppkID = "ID"
        const val groupName = "Name"
        const val groupDate = "CreationDate"

        const val PlayerTable = "player"
        const val PlayerpkID = "ID"
        const val PlayerName = "Name"
        const val PlayerRating = "Rating"

        const val GroupPlayerTable = "groupplayer"
        const val PlayerID = "playerID"
        const val GroupID = "mygroupID"
    }

    //The current context that created the db object
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

        var sqlCreatePlayer: String = "CREATE TABLE IF NOT EXISTS ${PlayerTable} " +
                "(${PlayerpkID} INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "${PlayerName} TEXT, " +
                "${PlayerRating} INTEGER);"

        if(db != null)
        {
            db.execSQL(sqlCreateGroup)
            db.execSQL(sqlCreatePlayer)
            CreateGroupPlayersTable(db)
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
        var sqlDeletePlayer: String = "DROP TABLE IF EXISTS ${PlayerTable}"
        var sqlDeleteGroupPlayerMapping : String = "DROP TABLE IF EXISTS $GroupPlayerTable"
        db.execSQL(sqlDeleteGroup)
        db.execSQL(sqlDeletePlayer)
        db.execSQL(sqlDeleteGroupPlayerMapping)
        onCreate(db)
    }

    /**
     * Run query on database to create the group-players db
     * @param db - database SQLiteHelper object
     */
    fun CreateGroupPlayersTable(db: SQLiteDatabase)
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var sql: String = "CREATE TABLE IF NOT EXISTS $GroupPlayerTable " +
                "($GroupID INTEGER, "+
                "$PlayerID INTEGER);"
        db.execSQL(sql)
    }


    ////////////////////////////////////////////////////////////////////////////////
    /**
     * GROUP CRUD EVENTS
     */
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Read and parse and Groups stored in the database
     */
    fun ReadAllGroups() : ArrayList<MyGroup>
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList = ArrayList<MyGroup>()

        // Select All Query
        var selectQuery = "SELECT * FROM ${groupTableName}"
        val db = this.readableDatabase;

        var cursor = db!!.rawQuery(selectQuery, null)

        if(cursor != null)
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
     * Read the information for an individual MyGroup object
     * @param id - a unique object for a MyGroup object
     */
    fun ReadMyGroupByID(id:Int) : MyGroup
    {
        var selectQuery: String = "SELECT * FROM $groupTableName WHERE $grouppkID = $id"
        val db = this.readableDatabase;

        var group : MyGroup

        var cursor: Cursor? = null

        try {
            cursor = db!!.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            //db.execSQL(SQL_CREATE_ENTRIES)
            //return ArrayList()
            return null!!
        }

        group = MyGroup()
        if (cursor!!.moveToFirst()) {
            group.ID = cursor.getInt(cursor.getColumnIndex(grouppkID))
            group.Name = cursor.getString(cursor.getColumnIndex(groupName))
        }
        cursor.close()
        return group
    }

    /**
     * INSERT a new Group to the database
     */
    @Throws(SQLiteConstraintException::class)
    fun CreateGroup(group: MyGroup) : Int
    {
        var result : Int = 0

        var values = ContentValues()
        values.put("Name", group.Name)

        val db = this.writableDatabase

        result = db!!.insert(groupTableName, "", values).toInt()

        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * Player CRUD EVENTS
     */
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Read all stored players and return
     * @return ArrayList of players
     */
    fun ReadAllPlayers() : ArrayList<Player>
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList = ArrayList<Player>()

        // Select All Query
        var selectQuery: String = "SELECT * FROM $PlayerTable"
        val db = this.readableDatabase

        var cursor = db!!.rawQuery(selectQuery, null)
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(PlayerpkID))
                    val name =cursor.getString(cursor.getColumnIndex(PlayerName))
                    val rating = cursor.getInt(cursor.getColumnIndex(PlayerRating))

                    arrayList.add(Player(id, name, rating))
                }
                while (cursor.moveToNext())
            }
        }
        cursor.close()
        return arrayList
    }

    /**
     * Insert a new player
     * @param player - Player object to be inserted
     * @return int
     */
    fun InsertPlayer(player: Player) : Int
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var values = ContentValues()
        values.put(PlayerName, player.Name)
        values.put(PlayerRating, player.Rating)

        val db = this.writableDatabase

        return db!!.insert(PlayerTable, "", values).toInt()
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * MyGroup Player Mapping Create and Read
     */
    ////////////////////////////////////////////////////////////////////////////////

    fun ReadAllGroupPlayerMappings()
    {

    }

}