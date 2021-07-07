package com.chuks.notes.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chuks.notes.databinding.NoteItemBinding

class NotesAdapter (val notes: List<Note>, val clicker: (Note) -> Unit): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){
   inner class NoteViewHolder (var binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (note : Note){
            binding.apply {
                idDisplay.text = note.id.toShort().toString()
                titleDisplay.text = note.title
                root.setOnClickListener{clicker(note)}
//listener.onClick(note)
                //clicker(note)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       var binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val record = notes[position]
        holder.bind(record)
    }

    override fun getItemCount(): Int {
      return notes.size
    }

    //interface OnNoteItemClickListener{
   //     fun onClick(note: Note)
   // }
}
