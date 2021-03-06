package com.example.seanrafferty.mygrouprandomiser.SQLite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.crashlytics.android.Crashlytics

class DatabaseHandler : SQLiteOpenHelper
{
    private val TAG = "DatabaseHandler"

    companion object {
        val DBName = "GroupDB.db"
        val DBVersion = 15

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
        const val EventCompleted = "completed"
        const val EventBalanced = "balanced"

        const val TeamTable = "team"
        const val TeampkID = "ID"
        const val TeamName = "Name"
        const val TeamEventID = "eventID"
        const val TeamScore = "score"

        const val PlayerSkillTable = "playerskill"
        const val PlayerSkillpkID = "ID"
        const val PlayerSkillName = "Name"
        const val PlayerSkillModifier = "Modifier"

        const val PlayerSkillMappingTable = "playerSkillMapping"
        const val PlayerSkillMappingPlayerID = "PlayerID"
        const val PlayerSkillMappingSkillID = "PlayerSkillID"

        const val TeamPlayerMappingTable = "teamPlayers"
        const val TeamID = "teamID"
        //playerID is already noted in group player mapping

    }

    //The current context that created the db object
    var context: Context? = null

    constructor(context: Context?) : super(context, DBName, null, DBVersion) {
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

        try
        {
            //Create the Group Table if it doesn't already exist
            var sqlCreateGroup: String =  "CREATE TABLE IF NOT EXISTS ${groupTableName} " +
                    "(${grouppkID} INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "${groupName} TEXT, " +
                    "${groupDate} TEXT);"

            var sqlCreatePlayer: String = "CREATE TABLE IF NOT EXISTS ${PlayerTable} " +
                    "(${PlayerpkID} INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "${PlayerName} TEXT, " +
                    "${PlayerRating} REAL);"

            if(db != null)
            {
                db.execSQL(sqlCreateGroup)
                db.execSQL(sqlCreatePlayer)
                CreateGroupPlayersTable(db)
                CreateEventTable(db)
                CreateTeamTable(db)
                CreateTeamPlayerMapping(db)
                CreatePlayerSkillsTable(db)
                CreatePlayerSkillMappingTable(db)

                Toast.makeText(context, "Database v$DBVersion", Toast.LENGTH_LONG).show()
            }
            else{
                println("database is null")
            }
        }
        catch(e: SQLiteException)
        {
            Crashlytics.log("SQL database failed to initialize")
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
        var sqlDeleteTeamPlayerMapping = "DROP TABLE IF EXISTS $TeamPlayerMappingTable"
        var sqlDeletePlayerSkillTable = "DROP TABLE IF EXISTS $PlayerSkillTable"
        var sqlDeletePlayerSkillMappingTable = "DROP TABLE IF EXISTS $PlayerSkillMappingTable"
        db.execSQL(sqlDeleteGroup)
        db.execSQL(sqlDeletePlayer)
        db.execSQL(sqlDeleteGroupPlayerMapping)
        db.execSQL(sqlDeleteEventTable)
        db.execSQL(sqlDeleteTeam)
        db.execSQL(sqlDeleteTeamPlayerMapping)
        db.execSQL(sqlDeletePlayerSkillTable)
        db.execSQL(sqlDeletePlayerSkillMappingTable)
        onCreate(db)
    }

    /**
     * Run query on database to create the group-players db
     * @param db - database SQLiteHelper object
     */
    private fun CreateGroupPlayersTable(db: SQLiteDatabase)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

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
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var sql : String = "CREATE TABLE IF NOT EXISTS $TeamTable " +
                "($TeampkID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$TeamName TEXT, " +
                "$TeamScore INT, " +
                "$TeamEventID INTEGER);"
        db.execSQL(sql)
    }

    /**
     * Create and execute script to create an group event
     */
    private fun CreateEventTable(db: SQLiteDatabase)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var sql : String = "CREATE TABLE IF NOT EXISTS $EventTable " +
                "($EventpkID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$EventDate TEXT, " +
                "$EventCompleted INTEGER, " +
                "$EventGroupID INTEGER," +
                "$EventBalanced INTEGER);"
        db.execSQL(sql)
    }

    /**
     * Operation to create the mapping table between players and teams
     */
    private fun CreateTeamPlayerMapping(db: SQLiteDatabase)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var sql : String = "CREATE TABLE IF NOT EXISTS $TeamPlayerMappingTable " +
                "($TeamID INTEGER, " +
                "$PlayerID INTEGER);"
        db.execSQL(sql)
    }

    /**
     * Initialisation of player skill data table
     */
    private fun CreatePlayerSkillsTable(db: SQLiteDatabase)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var sql : String = "CREATE TABLE IF NOT EXISTS $PlayerSkillTable " +
                "($PlayerSkillpkID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$PlayerSkillName TEXT," +
                "$PlayerSkillModifier REAL);"
        db.execSQL(sql)
    }

    /**
     * Initialisation of player skill to player mapping table
     */
    private fun CreatePlayerSkillMappingTable(db: SQLiteDatabase)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var sql : String = "CREATE TABLE IF NOT EXISTS $PlayerSkillMappingTable " +
                "($PlayerSkillMappingPlayerID INTEGER, " +
                "$PlayerSkillMappingSkillID INTEGER);"
        db.execSQL(sql)
    }
}