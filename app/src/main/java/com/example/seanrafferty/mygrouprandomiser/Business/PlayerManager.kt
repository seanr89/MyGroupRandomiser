package com.example.seanrafferty.mygrouprandomiser.Business

import android.content.Context
import android.util.Log
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Models.Player
import com.example.seanrafferty.mygrouprandomiser.Models.PlayerSkill
import com.example.seanrafferty.mygrouprandomiser.SQLite.DatabaseHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerDBHandler
import com.example.seanrafferty.mygrouprandomiser.SQLite.PlayerSkillDBHandler

/**
 * Provide business logic and layer to communicate between presentation and data layers
 * @param context : Activity Context to allow DBHelper integration
 */
class PlayerManager(val context: Context?)
{

    /**
     * Request all players
     * @return an ArrayList of players
     */
    fun ReadAllPlayers() : ArrayList<Player>
    {
//        var db = PlayerDBHandler(DatabaseHandler(context))
//        return db.ReadAllPlayers()
        return ReadAllPlayersAndSkills()
    }

    /**
     * Operation to request all players and assign the skills from mappings
     * @return An ArrayList of players
     */
    private fun ReadAllPlayersAndSkills() : ArrayList<Player>
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        var skillDB = PlayerSkillDBHandler(DatabaseHandler(context))

        //N.B. these could be async and awaited
        var players = db.ReadAllPlayers()
        var skills = skillDB.GetAllPlayerSkills()
        var playerSkillIDMappings = db.ReadAllPlayerSkillPlayerMappings()

        try
        {
            //now to loop through each player
            for(item : Player in players)
            {
                Log.d("Search", "current player is ${item.Name}")

                //filter the mappings firstly by the current player ID
                var filteredMappings = playerSkillIDMappings.filter{id -> id.playerID == item.ID}
                if(filteredMappings == null)
                {
                    Log.d("Search", "filter is null")
                }
                if(filteredMappings.isNotEmpty())
                {
                    //Then filter from all skills from the filtered mappings
                    var filteredSkills = skills.filter { it -> filteredMappings.any {a -> a.skillID == it.id}}

                    if(filteredSkills.isNotEmpty())
                    {
                        item.skills = filteredSkills as ArrayList<PlayerSkill>
                    }
                }
            }
        }
        catch(e : KotlinNullPointerException)
        {
            Log.e("Exception", "exception caught : ${e.message}")
        }

        return players
    }

    /**
     * Insert a new player into the database
     * @param player : the player to be inserted
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun SavePlayer(player : Player) : Int
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        return db.InsertPlayer(player)
    }

    /**
     * Insert mappings between a collection of players and an individual group
     * @param players :
     * @param group :
     */
    fun AssignPlayersToGroup(players : ArrayList<Player>, group : MyGroup)
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        db.AssignPlayersToGroup(players, group)
    }

    /**
     * handle the updating of an individual players rating
     * @param player :
     * @reutrn the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun UpdatePlayerRating(player : Player) : Int
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        return db.UpdatePlayerRating(player)
    }

    ////////////////////////////////////////////////////////////
    ///////////////////// PLAYER SKILLS ////////////////////////
    ////////////////////////////////////////////////////////////

    /**
     * Operation to query and return all available player skills
     * @return a list/collection of PlayerSkill objects
     */
    fun ReadAllAvailablePlayerSkills() : ArrayList<PlayerSkill>
    {
        var db = PlayerSkillDBHandler(DatabaseHandler(context))
        return db.GetAllPlayerSkills()
    }

    /**
     * Operation to save/map player skills to an individual player
     * @param skills : collection of playerskills
     * @param player : the player to insert into
     */
    fun SavePlayerSkillsToPlayer(skills : ArrayList<PlayerSkill>, player: Player)
    {
        var db = PlayerDBHandler(DatabaseHandler(context))
        if(skills.isNotEmpty())
        {
            db.DeletePlayerSkillMappingsForPlayer(player)

            for(item : PlayerSkill in skills)
            {
                db.InsertPlayerSkillToPlayer(player, item)
            }
        }
    }
}