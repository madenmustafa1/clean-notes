package com.maden.noteapp.presentation.note_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.maden.noteapp.R
import com.maden.noteapp.common.DateUtil
import com.maden.noteapp.common.Resource
import com.maden.noteapp.presentation.util.extensions.showToast
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.databinding.FragmentNoteDetailBinding
import com.maden.noteapp.presentation.util.extensions.downloadImageWithUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private val _noteDetailViewModel: NoteDetailViewModel by viewModels()
    private val _args: NoteDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNoteDetailBinding.bind(view)
        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding.noteInputLayout) {
            button.text = getText(R.string.ok)
            button.setOnClickListener {
                _noteDetailViewModel.insertNote(
                    NoteModel(
                        uuid = _args.noteId,
                        title = titleTextView.text.toString(),
                        description = descriptionTextView.text.toString(),
                        photoUrl = imageUrlTextView.text.toString(),
                        date = DateUtil.getCurrentDate(),
                        isEdited = true
                    )
                )
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            _noteDetailViewModel.getNote(_args.noteId).collect {
                setLayoutInput(it)
            }
        }

        _noteDetailViewModel.insertNoteLiveData().observe(viewLifecycleOwner) {
            navPopBackStack(it.message ?: getString(R.string.success))
        }
    }

    private fun setLayoutInput(result: Resource<NoteModel?>) {
        if (result is Resource.Error) {
            navPopBackStack(result.message)
            return
        }

        if (result.data == null) {
            navPopBackStack(result.message)
            return
        }

        result.data.let {
            binding.noteInputLayout.titleTextView.setText(it.title)
            binding.noteInputLayout.imageUrlTextView.setText(it.photoUrl)
            binding.noteInputLayout.descriptionTextView.setText(it.description)
            binding.noteInputLayout.editDateTextView.visibility = View.VISIBLE
            binding.noteInputLayout.editDateTextView.setText(it.date)
            it.photoUrl?.let { photoUrl ->
                if (photoUrl.isNotEmpty()) {
                    binding.noteInputLayout.animationView.visibility = View.GONE
                    binding.noteInputLayout.noteImage.visibility = View.VISIBLE
                    binding.noteInputLayout.noteImage.downloadImageWithUrl(it.photoUrl)
                }
            }
        }
    }

    private fun navPopBackStack(message: String?) {
        showToast(message ?: getString(R.string.general_error))
        requireView().findNavController().popBackStack()
    }
}