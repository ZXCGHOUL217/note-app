package com.example.notesapp.domain.use_case

import com.example.notesapp.domain.repository.NoteRepository

class ClearNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() = repository.clearNote()
}