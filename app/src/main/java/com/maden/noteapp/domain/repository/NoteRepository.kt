package com.maden.noteapp.domain.repository

import com.maden.noteapp.common.DbOrder
import com.maden.noteapp.common.Resource
import com.maden.noteapp.data.local.dto.NoteModel

interface NoteRepository {

    suspend fun getNotes(order: DbOrder): Resource<List<NoteModel>>

    suspend fun getNoteById(uuid: Int): Resource<NoteModel?>

    suspend fun insertNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)

    suspend fun insertAll(notes: List<NoteModel>)
}