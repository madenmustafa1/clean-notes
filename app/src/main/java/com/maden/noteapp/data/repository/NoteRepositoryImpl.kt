package com.maden.noteapp.data.repository

import com.maden.noteapp.common.DbOrder
import com.maden.noteapp.common.Resource
import com.maden.noteapp.data.local.NoteDao
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    private val _coroutineContext = Dispatchers.IO

    override suspend fun getNotes(order: DbOrder): Resource<List<NoteModel>> = withContext(_coroutineContext) {
        //Order
        return@withContext Resource.Success(dao.getNotes())
    }

    override suspend fun getNoteById(uuid: Int): Resource<NoteModel?> = withContext(_coroutineContext) {
        return@withContext Resource.Success(dao.getNoteById(uuid))
    }

    override suspend fun insertNote(note: NoteModel) = withContext(_coroutineContext) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: NoteModel)  = withContext(_coroutineContext) {
        dao.deleteNote(note)
    }

    override suspend fun insertAll(notes: List<NoteModel>) {
        dao.insertAll(notes)
    }
}