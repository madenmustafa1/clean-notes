package com.maden.noteapp.presentation.util.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maden.noteapp.data.local.dto.NoteModel
import com.maden.noteapp.databinding.NoteRecyclerItemBinding

class NoteListAdapter(private val _clickListener: NoteAdapterListener) :
    ListAdapter<NoteModel, NoteListAdapter.ViewHolder>(TaskDiffCallBack()) {

    private lateinit var _binding: NoteRecyclerItemBinding

    private class TaskDiffCallBack : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(view: NoteRecyclerItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        _binding = NoteRecyclerItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(_binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        _binding.noteModel = getItem(position)
        _binding.noteItemLayout.setOnClickListener {
            _clickListener.editClickListener(note = getItem(position))
        }
        _binding.deleteButton.setOnClickListener {
            if (currentList.size <= viewHolder.adapterPosition) return@setOnClickListener
            _clickListener.removeListener(note = getItem(viewHolder.adapterPosition), position = viewHolder.adapterPosition)
        }
    }

    fun deleteItem(position: Int) {
        val currentList = currentList.toMutableList()
        if (currentList.size <= position) return
        currentList.removeAt(position)
        submitList(currentList)
    }

}