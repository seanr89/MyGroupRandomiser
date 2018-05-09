# MyGroupRandomiser
A new repo to create a randomisation app for creating 5 a side teams

Initial Development is focused on Android using Kotlin

Features to be designed for phase 1 include:
1. Use Local SQLiteDatabase
2. Create a new sport/football group
3. Create and assign players to groups
4. Create events and generate teams

Application will be broken into a number of phases:
Phase 1
- Basic Android App written in Kotlin
- SQLite implementation working
- Creation of groups
- Creation of players
- Creation of events
- Creation of teams

Phase 2
- Handle data rating
- Handle storing of team results

Phase 2
- Include firebase
- Removal of Data Initialisation Content
- Authentication and Authorisation

Phase 3
- Publish to App Store
- Location Data


Process Flow (Phase 1)
1. Create a Group to allow players to join/be assigned too
  a. A group is a collection of players who play together at scheduled group events
2. Allow players to be created
3. Assign a player to a group
4. Create an event for a group
5. Select Date, Time and Players too Attend
6. Randomize teams
  a. Step 1 is standard random shuffle
  b. step 2 is to shuffle with ratings to also Assist
7. Save Teams to Events
8. View event and update statistics
  a. event complete
  b. goals scored
