package com.chuks.notes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chuks.notes.models.Note
import com.chuks.notes.models.NoteDatabase

class MainActivityViewModel:ViewModel() {
    val notesLiveData = MutableLiveData<List<Note>>()
    fun getNotes (database: NoteDatabase){
        notesLiveData.postValue(database.noteDao().getAllNotes())
    }
    fun addNote(database: NoteDatabase, note: Note){
        database.noteDao().addNote(note)
        getNotes(database)
    }
}