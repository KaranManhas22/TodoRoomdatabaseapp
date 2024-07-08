package com.karan.todoroomdatabaseapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var title:String?=null,
    var description:String?=null,
    var priority:Int=0

)
