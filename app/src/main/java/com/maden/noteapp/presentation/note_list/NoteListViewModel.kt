package com.maden.noteapp.presentation.note_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maden.noteapp.common.DbOrder
import com.maden.noteapp.common.Resource
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val _noteUseCase : NoteUseCase
) : ViewModel() {

    private val _noteListMutableLiveData = MutableLiveData<List<NoteModel>>()
    fun noteListLiveData(): LiveData<List<NoteModel>> = _noteListMutableLiveData
    fun getNoteList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = _noteUseCase.getNotes(DbOrder.DESCENDING)
            if (response is Resource.Success) {
                response.data?.let {
                    _noteListMutableLiveData.postValue(it)
                    return@launch
                }
            }
            _noteListMutableLiveData.postValue(arrayListOf())
        }
    }

    private val _deleteNoteMutableLiveData = MutableLiveData<Int>()
    fun deleteNoteLiveData() = _deleteNoteMutableLiveData

    fun deleteNote(noteModel: NoteModel, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _noteUseCase.deleteNote(noteModel)
            _deleteNoteMutableLiveData.postValue(position)
        }
    }

}