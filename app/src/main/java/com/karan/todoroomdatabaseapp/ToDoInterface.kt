package com.karan.todoroomdatabaseapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoInterface {
    @Query("Select * from ToDoEntity")
    fun getList(): List<ToDoEntity>

    @Insert
    fun insertValue(toDoEntity: ToDoEntity)



}