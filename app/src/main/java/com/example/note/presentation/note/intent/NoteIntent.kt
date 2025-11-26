package com.example.notesapp.presentation.note.intent

sealed class NoteIntent {
    data class UpdateNoteContent(val content: String) : NoteIntent()
    data class UpdateSearchQuery(val query: String) : NoteIntent()
    object SaveNote : NoteIntent()
    object ClearNote : NoteIntent()
    object LoadNote : NoteIntent()
}