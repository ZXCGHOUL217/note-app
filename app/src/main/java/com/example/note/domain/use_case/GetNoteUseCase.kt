package com.example.notesapp.domain.use_case

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<Note> = repository.getNote()
}