package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "noteTable")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title: String = "",
    val noteText: String = "",
    val date: String = "",
    val color: String = ""
)
