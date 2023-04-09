package com.maden.noteapp.presentation.note_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maden.noteapp.R
import com.maden.noteapp.presentation.util.extensions.showToast
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.databinding.FragmentNoteListBinding
import com.maden.noteapp.presentation.util.adapters.NoteAdapterListener
import com.maden.noteapp.presentation.util.adapters.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list), NoteAdapterListener {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val _noteListViewModel: NoteListViewModel by viewModels()
    private var _noteListAdapter: NoteListAdapter = NoteListAdapter(_clickListener = this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNoteListBinding.bind(view)
        initViews()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        _noteListViewModel.getNoteList()
    }

    private fun observeData() {
        _noteListViewModel.noteListLiveData().observe(viewLifecycleOwner) {
            if (!isAdded) return@observe

            if (it.isEmpty()) {
                //Add Note imageView
                return@observe
            }

            _noteListAdapter.submitList(it.toMutableList())
        }

        _noteListViewModel.deleteNoteLiveData().observe(viewLifecycleOwner) {
            if (!isAdded) return@observe
            _noteListAdapter.deleteItem(it)
        }
    }

    private fun initViews() {
        binding.noteListRecyclerView.adapter = _noteListAdapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.noteListRecyclerView.layoutManager = linearLayoutManager

        binding.addNoteButton.setOnClickListener {
            val action = NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment()
            requireView().findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun editClickListener(note: NoteModel) {
        if (note.uuid == null) {
            showToast(getString(R.string.general_error))
            return
        }
        val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(note.uuid)
        requireView().findNavController().navigate(action)
    }

    override fun removeListener(note: NoteModel, position: Int) {
        _noteListViewModel.deleteNote(noteModel = note, position = position)
    }

}