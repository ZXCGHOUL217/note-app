package com.example.notesapp.data.local.dao

import androidx.room.*
import com.example.notesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE id = 1")
    fun getNote(): Flow<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = 1")
    suspend fun deleteNote()
}