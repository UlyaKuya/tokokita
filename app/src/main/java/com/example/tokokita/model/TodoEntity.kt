package com.example.tokokita.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val judul: String,

    val selesai: Boolean = false
)