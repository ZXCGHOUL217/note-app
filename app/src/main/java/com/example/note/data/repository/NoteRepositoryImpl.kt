package com.example.notesapp.data.repository

import com.example.notesapp.data.local.database.NoteDatabase
import com.example.notesapp.data.local.entity.NoteEntity
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val database: NoteDatabase
) : NoteRepository {

    private val noteDao = database.noteDao()

    override fun getNote(): Flow<Note> {
        return noteDao.getNote().map { entity ->
            entity?.toNote() ?: Note()
        }
    }

    override suspend fun saveNote(note: Note) {
        noteDao.upsertNote(note.toEntity())
    }

    override suspend fun clearNote() {
        noteDao.deleteNote()
    }

    private fun NoteEntity.toNote(): Note {
        return Note(
            content = content,
            lastUpdated = lastUpdated
        )
    }

    private fun Note.toEntity(): NoteEntity {
        return NoteEntity(
            content = content,
            lastUpdated = lastUpdated
        )
    }
}