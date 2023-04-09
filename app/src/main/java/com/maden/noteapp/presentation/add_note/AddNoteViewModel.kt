package com.maden.noteapp.presentation.add_note

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val _noteUseCase: NoteUseCase
) : ViewModel() {

    private val _resultMutableLiveData = MutableLiveData<Resource<Boolean>>()
    fun resultLiveData(): LiveData<Resource<Boolean>> = _resultMutableLiveData

    fun addNote(noteModel: NoteModel) {
        if (noteModel.title.trim().isEmpty()) {
            _resultMutableLiveData.postValue(Resource.Error("Title cannot be empty!"))
            return
        }

        if (noteModel.description.trim().isEmpty()) {
            _resultMutableLiveData.postValue(Resource.Error("Description cannot be empty!"))
            return
        }

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _noteUseCase.insertNote(noteModel)
            _resultMutableLiveData.postValue(Resource.Success(true))
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _resultMutableLiveData.postValue(Resource.Error("An error occurred! ${exception.localizedMessage}"))
    }
}