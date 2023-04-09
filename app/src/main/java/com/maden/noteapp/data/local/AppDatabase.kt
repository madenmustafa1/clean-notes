package com.maden.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maden.noteapp.data.local.dto.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "note_app"
    }
}