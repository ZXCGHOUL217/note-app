package com.example.notesapp.presentation.note.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.local.database.NoteDatabase
import com.example.notesapp.data.repository.NoteRepositoryImpl
import com.example.notesapp.domain.use_case.ClearNoteUseCase
import com.example.notesapp.domain.use_case.GetNoteUseCase
import com.example.notesapp.domain.use_case.SaveNoteUseCase
import com.example.notesapp.presentation.note.intent.NoteIntent
import com.example.notesapp.presentation.note.state.NoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteRepositoryImpl(
        NoteDatabase.getInstance(application.applicationContext)
    )

    private val getNoteUseCase = GetNoteUseCase(repository)
    private val saveNoteUseCase = SaveNoteUseCase(repository)
    private val clearNoteUseCase = ClearNoteUseCase(repository)

    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteState> = _state.asStateFlow()

    init {
        setupNoteObserver()
    }

    fun onIntent(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.UpdateNoteContent -> {
                updateNoteContent(intent.content)
            }
            is NoteIntent.UpdateSearchQuery -> {
                updateSearchQuery(intent.query)
            }
            NoteIntent.SaveNote -> {
                saveNote()
            }
            NoteIntent.ClearNote -> {
                clearNote()
            }
            NoteIntent.LoadNote -> {
            }
        }
    }

    private fun updateNoteContent(content: String) {
        _state.value = _state.value.copy(
            note = _state.value.note.copy(content = content)
        )
        viewModelScope.launch {
            saveNoteUseCase(_state.value.note)
        }
    }

    private fun updateSearchQuery(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                saveNoteUseCase(_state.value.note)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Ошибка сохранения: ${e.message}")
            }
        }
    }

    private fun clearNote() {
        viewModelScope.launch {
            try {
                clearNoteUseCase()
                _state.value = _state.value.copy(
                    note = NoteState().note,
                    searchQuery = ""
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Ошибка очистки: ${e.message}")
            }
        }
    }

    private fun setupNoteObserver() {
        getNoteUseCase()
            .onEach { note ->
                _state.value = _state.value.copy(
                    note = note,
                    isLoading = false,
                    error = null
                )
            }
            .launchIn(viewModelScope)
    }
}