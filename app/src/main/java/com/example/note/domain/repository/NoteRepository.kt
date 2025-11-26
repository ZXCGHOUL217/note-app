package com.example.notesapp.domain.repository

import com.example.notesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNote(): Flow<Note>
    suspend fun saveNote(note: Note)
    suspend fun clearNote()
}