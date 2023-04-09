package com.maden.noteapp.domain.use_case

import com.maden.noteapp.common.DateUtil
import com.maden.noteapp.common.DbOrder
import com.maden.noteapp.common.Resource
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.domain.repository.FakeNoteRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NoteUseCaseTest {
    private lateinit var noteUseCase: NoteUseCase
    private lateinit var noteRepository: FakeNoteRepository

    @Before
    fun setup() {
        noteRepository = FakeNoteRepository()
        noteUseCase = NoteUseCase(noteRepository)
    }

    @Test
    fun `getNotes should return success when repository returns data`() = runBlocking {
        // Given
        val notes = listOf(
            NoteModel(1, "Note 1", "Note 1 Desc", date = DateUtil.getCurrentDate()),
            NoteModel(2, "Note 2", "Note 2 Desc", date = DateUtil.getCurrentDate()),
            NoteModel(2, "Note 3", "Note 3 Desc", date = DateUtil.getCurrentDate()),
        )

        noteRepository.insertAll(notes)

        // When
        val result = noteUseCase.getNotes(DbOrder.DESCENDING)

        // Then
        assertTrue(result is Resource.Success)
        assertEquals(notes, (result as Resource.Success).data)
    }

    @Test
    fun `getNoteById should return success when repository returns data`() = runBlocking {
        // Given
        val note = NoteModel(1, "Note 1", "Note 1 Desc", date = DateUtil.getCurrentDate())
        noteRepository.insertNote(note)

        // When
        val result = noteUseCase.getNoteById(1)

        // Then
        assertTrue(result is Resource.Success)
        assertEquals(note, (result as Resource.Success).data)
    }

    @Test
    fun `insertNote should add note to repository`() = runBlocking {
        // Given
        val note = NoteModel(1, "Note 1", "Note 1 Desc", date = DateUtil.getCurrentDate())

        // When
        noteUseCase.insertNote(note)

        // Then
        val result = noteRepository.getNotes(DbOrder.DESCENDING)
        assertTrue(result is Resource.Success)
        assertEquals(listOf(note), (result as Resource.Success).data)
    }

    @Test
    fun `deleteNote should remove note from repository`() = runBlocking {
        // Given
        val note = NoteModel(1, "Note 1", "Note 1 Desc", date = DateUtil.getCurrentDate())
        noteRepository.insertNote(note)

        // When
        noteUseCase.deleteNote(note)

        // Then
        val result = noteRepository.getNotes(DbOrder.DESCENDING)
        assertTrue(result is Resource.Success)
        assertTrue(!(result as Resource.Success).data!!.contains(note))
    }
}