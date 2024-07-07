package com.karan.todoroomdatabaseapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoEntity::class], version = 1, exportSchema = true)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todointerface(): ToDoInterface

    companion object {
        private var toDoDatabase: ToDoDatabase? = null
        fun getInstance(context: Context): ToDoDatabase {
            if (toDoDatabase == null) {
                toDoDatabase =
                    Room.databaseBuilder(context, ToDoDatabase::class.java, "RoomDatabase")
                        .allowMainThreadQueries()
                        .build()
            }
            return toDoDatabase!!
        }
    }

}