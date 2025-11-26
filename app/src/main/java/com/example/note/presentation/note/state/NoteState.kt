package com.example.notesapp.presentation.note.state

import com.example.notesapp.domain.model.Note

data class NoteState(
    val note: Note = Note(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)