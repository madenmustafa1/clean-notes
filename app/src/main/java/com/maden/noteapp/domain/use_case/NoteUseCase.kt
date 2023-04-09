package com.maden.noteapp.domain.use_case

import com.maden.noteapp.common.DbOrder
import com.maden.noteapp.common.Resource
import com.maden.noteapp.common.checkData
import com.maden.noteapp.common.checkUuid
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteUseCase(
    private val repository: NoteRepository
) : NoteRepository {

    private val _coroutineContext = Dispatchers.IO

    override suspend fun getNotes(order: DbOrder): Resource<List<NoteModel>> =
        withContext(_coroutineContext) {
            return@withContext repository.getNotes(order = order)
        }

    override suspend fun getNoteById(uuid: Int): Resource<NoteModel?> =
        withContext(_coroutineContext) {
            uuid.checkUuid()
            return@withContext repository.getNoteById(uuid = uuid)
        }

    override suspend fun insertNote(note: NoteModel) = withContext(_coroutineContext) {
        note.uuid?.checkUuid()
        note.title.checkData()
        return@withContext repository.insertNote(note = note)
    }

    override suspend fun deleteNote(note: NoteModel) = withContext(_coroutineContext) {
        note.uuid?.checkUuid()
        return@withContext repository.deleteNote(note)
    }

    override suspend fun insertAll(notes: List<NoteModel>) = withContext(_coroutineContext) {
        for (i in notes) {
            i.uuid?.checkUuid()
            i.title.checkData()
        }
        return@withContext repository.insertAll(notes = notes)
    }


}