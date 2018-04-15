package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player

class DatabaseHandler : SQLiteOpenHelper
{

    companion object {
        val DBName = "GroupDB.db"
        val DBVersion = 9

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

        //Event
        const val EventTable = "event"
        const val EventpkID = "ID"
        const val EventDate = "Date"
        const val EventGroupID = "mygroupID"

        const val TeamTable = "team"
        const val TeamID = "ID"
        const val TeamName = "Name"
        const val TeamEventID = "eventID"

    }

    //The current context that created the db object
    var context: Context? = null

    constructor(context: Context) : super(context, DBName, null, DBVersion) {
        this.context = context
    }

    /**
     * provide a writable database handler object
     * @return : writable SQLiteDatabase
     */
    fun GetWritableDataBaseObject() : SQLiteDatabase
    {
        return this.writableDatabase
    }

    /**
     * provide a readable database handler object
     * @return : readable SQLiteDatabase
     */
    fun GetReadableDataBaseObject() : SQLiteDatabase
    {
        return this.readableDatabase
    }

    /**
     * Handle on DB creation - this is called each time the DB handler is instantiated
     */
    override fun onCreate(db: SQLiteDatabase)
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name + " For Version " + DBVersion)

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
            CreateEventTable(db)
            CreateTeamTable(db)

            Toast.makeText(context, "Database v$DBVersion", Toast.LENGTH_LONG).show()
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
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var sqlDeleteGroup = "DROP TABLE IF EXISTS '$groupTableName'"
        var sqlDeletePlayer = "DROP TABLE IF EXISTS ${PlayerTable}"
        var sqlDeleteGroupPlayerMapping = "DROP TABLE IF EXISTS $GroupPlayerTable"
        var sqlDeleteEventTable = "DROP TABLE IF EXISTS $EventTable"
        var sqlDeleteTeam = "DROP TABLE IF EXISTS $TeamTable"
        db.execSQL(sqlDeleteGroup)
        db.execSQL(sqlDeletePlayer)
        db.execSQL(sqlDeleteGroupPlayerMapping)
        db.execSQL(sqlDeleteEventTable)
        db.execSQL(sqlDeleteTeam)
        onCreate(db)

        var dbInitData = InitialiseDataDBHandler(this)
        dbInitData.RunDataInitialisation()
    }

    /**
     * Run query on database to create the group-players db
     * @param db - database SQLiteHelper object
     */
    private fun CreateGroupPlayersTable(db: SQLiteDatabase)
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var sql: String = "CREATE TABLE IF NOT EXISTS $GroupPlayerTable " +
                "($GroupID INTEGER, "+
                "$PlayerID INTEGER);"
        db.execSQL(sql)
    }

    /**
     * Create and execute script to create the team table
     */
    private fun CreateTeamTable(db: SQLiteDatabase)
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var sql : String = "CREATE TABLE IF NOT EXISTS $TeamTable " +
                "($TeamID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$TeamName TEXT, " +
                "$TeamEventID INTEGER);"
        db.execSQL(sql)
    }

    /**
     * Create and execute script to create an group event
     */
    private fun CreateEventTable(db: SQLiteDatabase)
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var sql : String = "CREATE TABLE IF NOT EXISTS $EventTable " +
                "($EventpkID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$EventDate TEXT, " +
                "$EventGroupID INTEGER);"
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
     * @param group - the group to find players
     * @return MutableList<Player>
     */
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Read all the players for a provided group
     */
    fun ReadAllPlayersForAGroup(group:MyGroup) : MutableList<Player>
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var modelList: MutableList<Player> = mutableListOf()

        //Get all of the player IDs
        var playerIDs = ReadAllPlayerIDsForGroup(group)

        //Get all Players
        var players = ReadAllPlayers()

        for(item: Int in playerIDs)
        {
            var player = players.filter { it.ID == item } as Player
            if(player != null)
            {
                modelList.add(player)
            }
        }
        return modelList
    }

    /**
     * Read all assigned group IDs for a single player
     * @param player : the player to query all group IDs mapped
     */
    fun ReadAllGroupIDsForPlayer(player:Player) : MutableList<Int>
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList: MutableList<Int> = mutableListOf<Int>()

        // Select query with where statement on playerID
        var selectQuery = "SELECT * FROM $GroupPlayerTable WHERE $PlayerID = ${player.ID}"
        val db = this.readableDatabase

        //initialise nullable cursor
        var cursor: Cursor?
        try
        {
            //execute the select query
            cursor = db!!.rawQuery(selectQuery, null)
            //check the cursor is notnull and can move to the first item (could be risky)
            if (cursor != null && cursor.moveToFirst())
            {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(GroupID))
                    arrayList.add(id)
                }
                while (cursor.moveToNext())
            }
        }
        catch (e: SQLiteException)
        {
            // if cursor has a sql exception
            Log.e(object{}.javaClass.enclosingMethod.name, e.message)
            return null!!
        }

        return arrayList
    }

    /**
     * Read all assigned player ids for a group
     * @param myGroup : the group to query for players assigned
     */
    fun ReadAllPlayerIDsForGroup(myGroup:MyGroup) : MutableList<Int>
    {
        Log.d("DatabaseHandler", object{}.javaClass.enclosingMethod.name)

        var arrayList: MutableList<Int> = mutableListOf<Int>()
        // Select query with where in the clause
        var selectQuery: String = "SELECT * FROM $GroupPlayerTable WHERE $GroupID = ${myGroup.ID}"
        val db = this.readableDatabase

        var cursor: Cursor?
        try
        {
            cursor = db!!.rawQuery(selectQuery, null)

            if (cursor != null && cursor.moveToFirst()) {
                do
                {
                    val id = cursor.getInt(cursor.getColumnIndex(PlayerID))
                    arrayList.add(id)
                }
                while (cursor.moveToNext())
            }
        }
        catch (e: SQLiteException)
        {
            // if cursor has a sql exception
            Log.e(object{}.javaClass.enclosingMethod.name, e.message)
            return null!!
        }

        return arrayList
    }

}