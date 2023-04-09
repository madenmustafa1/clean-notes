package com.maden.noteapp.presentation.util.adapters

import com.maden.noteapp.data.local.dto.NoteModel

interface NoteAdapterListener {
    fun editClickListener(note: NoteModel)
    fun removeListener(note: NoteModel, position: Int)
}