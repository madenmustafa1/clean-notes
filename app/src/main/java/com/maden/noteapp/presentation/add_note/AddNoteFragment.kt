package com.maden.noteapp.presentation.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.maden.noteapp.R
import com.maden.noteapp.common.DateUtil
import com.maden.noteapp.common.Resource
import com.maden.noteapp.presentation.util.extensions.showToast
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val _addNoteViewModel: AddNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddNoteBinding.bind(view)
        initViews()
        observeData()
    }

    private fun observeData() {
        _addNoteViewModel.resultLiveData().observe(viewLifecycleOwner) {
            if (it is Resource.Error) {
                showToast(message = it.message ?: getString(R.string.general_error))
                return@observe
            }

            showToast(message = getString(R.string.note_added))
            requireView().findNavController().popBackStack()
        }
    }

    private fun initViews() {
        with(binding.noteInputLayout) {
            button.text = getText(R.string.add)
            button.setOnClickListener {
                _addNoteViewModel.addNote(
                    NoteModel(
                        title = titleTextView.text.toString(),
                        description = descriptionTextView.text.toString(),
                        photoUrl = imageUrlTextView.text.toString(),
                        date = DateUtil.getCurrentDate(),
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}