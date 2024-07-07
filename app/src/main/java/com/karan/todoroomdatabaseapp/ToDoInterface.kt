package com.karan.todoroomdatabaseapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoInterface {


    @Insert
    fun insertValue(toDoEntity: ToDoEntity)
    @Delete
    fun deleteValue(toDoEntity: ToDoEntity)
    @Update
    fun updateValue(toDoEntity: ToDoEntity)

    @Query("Select * from ToDoEntity")
    fun getList(): List<ToDoEntity>

}