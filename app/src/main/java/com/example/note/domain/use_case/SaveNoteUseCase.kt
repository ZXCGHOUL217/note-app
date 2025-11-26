package com.example.notesapp.domain.use_case

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository

class SaveNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.saveNote(note)
}