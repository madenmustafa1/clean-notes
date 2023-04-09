package com.maden.noteapp.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.maden.noteapp.common.DateUtil
import com.maden.noteapp.data.local.AppDatabase
import com.maden.noteapp.data.local.NoteDao
import com.maden.noteapp.data.local.dto.NoteModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class NoteDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun initDb() {
        // In-memory database is used for testing to avoid disk persistence
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        noteDao = database.userDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetNotes() = runBlocking {
        // Given
        val notes = listOf(
            NoteModel(
                uuid = 1,
                title = "Note 1",
                description = "Note 1 Desc",
                date = DateUtil.getCurrentDate()
            ),
            NoteModel(
                uuid = 2,
                title = "Note 2",
                description = "Note 2 Desc",
                date = DateUtil.getCurrentDate()
            ),
            NoteModel(
                uuid = 3,
                title = "Note 3",
                description = "Note 3 Desc",
                date = DateUtil.getCurrentDate()
            ),
        )

        // When
        noteDao.insertAll(notes)
        val retrievedNotes = noteDao.getNotes()

        // Then
        assertEquals(notes.size, retrievedNotes.size)
        assertEquals(notes, retrievedNotes)
    }

    @Test
    fun insertAndGetNoteById() = runBlocking {
        // Given
        val note = NoteModel(
            uuid = 1,
            title = "Note 1",
            description = "Note 1 Desc",
            date = DateUtil.getCurrentDate()
        )
        noteDao.insertNote(note)

        // When
        val retrievedNote = noteDao.getNoteById(note.uuid!!)

        // Then
        assertEquals(note, retrievedNote)
    }

    @Test
    fun insertAndDeleteNote() = runBlocking {
        // Given
        val note = NoteModel(
            uuid = 1,
            title = "Note 1",
            description = "Note 1 Desc",
            date = DateUtil.getCurrentDate()
        )
        noteDao.insertNote(note)

        // When
        noteDao.deleteNote(note)
        val retrievedNote = noteDao.getNoteById(note.uuid!!)

        // Then
        assertNull(retrievedNote)
    }
}
