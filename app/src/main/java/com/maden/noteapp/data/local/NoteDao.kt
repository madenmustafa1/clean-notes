package com.maden.noteapp.data.local

import androidx.room.*
import com.maden.noteapp.data.local.dto.NoteModel


@Dao
interface NoteDao {
    @Query("SELECT * FROM note") // Optional ORDER BY uuid DESC
    suspend fun getNotes(): List<NoteModel>

    @Query("SELECT * FROM note WHERE uuid = :uuid")
    suspend fun getNoteById(uuid: Int): NoteModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<NoteModel>)

    @Delete
    suspend fun deleteNote(note: NoteModel)
}