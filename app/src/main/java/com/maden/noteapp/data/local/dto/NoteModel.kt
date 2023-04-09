package com.maden.noteapp.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note")
data class NoteModel(
    @PrimaryKey(autoGenerate = true) val uuid: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String? = null,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "is_edited") val isEdited: Boolean = false,
)
