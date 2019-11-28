package com.example.streak

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

//sqlite is implemented into android. databasehandler file creates the table to hold information in

//database name and column names
const val DATABASE_NAME = "GoalKeeperDB"
const val TABLE_NAME = "Goals"
const val COL_ID = "id"
const val COL_STREAK = "streak"
const val COL_GOAL = "goal"
const val COL_DURATION = "duration"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        //sql code to be passed on to sql in the form of a string
        //create table with given variables, ID is unique field, and the number increments itself automatically every time a new entry is made
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_GOAL + " VARCHAR(256)," +
                COL_STREAK + " INTEGER," +
                COL_DURATION + " FLOAT)"
        //code sent to sql to execute
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //method to insert data in to the table created
    fun insertData(goal : Goal) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_GOAL, goal.goal)
        cv.put(COL_STREAK, goal.streak)
        cv.put(COL_DURATION, goal.duration)
        var result = db.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): MutableList<Goal> {
        var list : MutableList<Goal> = ArrayList()

        var db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do {
                var goal = Goal()
                goal.id = result.getString(0).toInt()
                goal.goal = result.getString(1)
                goal.streak = result.getString(2).toInt()
                goal.duration = result.getString(3).toLong()
                list.add(goal)
            } while(result.moveToNext())

        }
         db.close()

        return list
    }

    fun deleteData(id : Int){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, COL_ID+"=?", arrayOf(id.toString()))
    }
}