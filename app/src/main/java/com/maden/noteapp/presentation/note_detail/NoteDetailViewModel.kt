package com.maden.noteapp.presentation.note_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maden.noteapp.common.Resource
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val _noteUseCase : NoteUseCase
): ViewModel() {

    private val _insertNoteMutableLiveData = MutableLiveData<Resource<Boolean>>()
    fun insertNoteLiveData() : LiveData<Resource<Boolean>> = _insertNoteMutableLiveData

    fun getNote(noteId: Int) = flow {
        val response = _noteUseCase.getNoteById(uuid = noteId)
        emit(response)
    }

    fun insertNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _noteUseCase.insertNote(note = noteModel)
            _insertNoteMutableLiveData.postValue(Resource.Success(true))
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _insertNoteMutableLiveData.postValue(Resource.Error("An error occurred! ${exception.localizedMessage}"))
    }
}