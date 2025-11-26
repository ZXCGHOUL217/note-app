package com.example.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    val id: Int = 1,
    val content: String,
    val lastUpdated: Long = System.currentTimeMillis()
)