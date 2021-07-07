package com.chuks.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.chuks.notes.databinding.ActivityMainBinding
import com.chuks.notes.models.Note
import com.chuks.notes.models.NoteDatabase
import com.chuks.notes.models.NotesAdapter
import com.chuks.notes.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
   private lateinit var database:NoteDatabase
    private lateinit var notesAdapter : NotesAdapter
    private lateinit var viewModel : MainActivityViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // instantiating database
        database = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes_database"
        ).allowMainThreadQueries().build()

        // instatanting viewModel
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getNotes(database)
       // observe live data from view model
        viewModel.notesLiveData.observe(this, { notes ->
            //creating adapter
            notesAdapter = NotesAdapter(database.noteDao().getAllNotes()) {
                val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
                intent.run {
                    putExtra("id", it.id)

                }
                startActivity(intent)
            }
            // refreshing the adapter
        })
        notesAdapter = NotesAdapter(database.noteDao().getAllNotes()){
               val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            intent.run {
                putExtra("id", it.id)
              startActivity(this)
             }
        }

        binding.notesRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesAdapter
        }
        binding.button.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val content = binding.contentItem.text.toString()

            saveNote(title, content)
        }
    }
    private fun saveNote(title: String, content: String){
         val note = Note(id=0, title, content)
      viewModel.addNote(database, note)
       // database.noteDao().addNote(note)
       // notesAdapter.notifyDataSetChanged()
    }
    //private val listener = object: NotesAdapter.OnNoteItemClickListener{
      //  override fun onClick(note: Note) {
         //   val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            //intent.run {
            //    putExtra("id", note.id)
             //   startActivity(this)
           // }
        //}

   // }

}