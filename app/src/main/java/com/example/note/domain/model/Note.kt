package com.example.notesapp.domain.model

data class Note(
    val content: String = "",
    val lastUpdated: Long = System.currentTimeMillis()
)