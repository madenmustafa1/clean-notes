package com.maden.noteapp.domain.repository

import com.maden.noteapp.common.DbOrder
import com.maden.noteapp.common.Resource
import com.maden.noteapp.common.checkData
import com.maden.noteapp.common.checkUuid
import com.maden.noteapp.data.local.dto.NoteModel

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<NoteModel>()

    override suspend fun getNotes(order: DbOrder): Resource<List<NoteModel>> {
        return Resource.Success(notes.toList())
    }

    override suspend fun getNoteById(uuid: Int): Resource<NoteModel?> {
        val note = notes.find { it.uuid == uuid }
        return if (note != null) {
            Resource.Success(note)
        } else {
            Resource.Error("Note not found")
        }
    }

    override suspend fun insertNote(note: NoteModel) {
        for (i in notes) {
            i.uuid?.checkUuid()
            i.title.checkData()
        }
        notes.add(note)
    }

    override suspend fun deleteNote(note: NoteModel) {
        notes.remove(note)
    }

    override suspend fun insertAll(notes: List<NoteModel>) {
        for (i in notes) {
            i.uuid?.checkUuid()
            i.title.checkData()
        }
        this.notes.addAll(notes)
    }

}