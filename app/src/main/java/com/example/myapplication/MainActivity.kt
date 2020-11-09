package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.myapplication.adapters.NoteAdapter
import com.example.myapplication.entities.Note
import com.example.myapplication.viewModel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteAdapter.ItemClicked {

    private lateinit var noteViewModel: NoteViewModel
    private val newWordActivityRequestCode = 1
    private val UpdateNoteActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = NoteAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // view model
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { notes ->
            // Update the cached copy of the words in the adapter.
            notes?.let { adapter.setNotes(it) }
        })

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNote::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val pdelete = data?.getStringExtra(com.example.myapplication.Update.EXTRA_REPLY_DELETE)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val ptitulo = data?.getStringExtra(AddNote.EXTRA_REPLY_TITULO)
            val ptexto = data?.getStringExtra(AddNote.EXTRA_REPLY_TEXTO)
            val pdata = data?.getStringExtra(AddNote.EXTRA_REPLY_DATA)


            if (ptitulo != null && ptexto != null && pdata != null) {
                val note = Note(titulo = ptitulo, texto = ptexto, data = pdata)
                noteViewModel.insert(note)
            }

        } else if (requestCode == UpdateNoteActivityRequestCode && resultCode == Activity.RESULT_OK && pdelete == "update") {
            val pid = data?.getIntExtra(com.example.myapplication.Update.EXTRA_REPLY_ID, -1)
            val ptitulo = data?.getStringExtra(com.example.myapplication.Update.EXTRA_REPLY_TITULO)
            val ptexto = data?.getStringExtra(com.example.myapplication.Update.EXTRA_REPLY_TEXTO)
            val pdata = data?.getStringExtra(com.example.myapplication.Update.EXTRA_REPLY_DATA)


            if (pid != null && ptitulo != null && ptexto != null && pdata != null) {
                val note = Note(id = pid, titulo = ptitulo, texto = ptexto, data = pdata)
                noteViewModel.updateNote(note)
            }
            } else if (pdelete == "delete") {
            val pid = data?.getIntExtra(com.example.myapplication.Update.EXTRA_REPLY_ID, -1)
            val ptitulo = data?.getStringExtra(com.example.myapplication.Update.EXTRA_REPLY_TITULO)
            val ptexto = data?.getStringExtra(com.example.myapplication.Update.EXTRA_REPLY_TEXTO)
            val pdata = data?.getStringExtra(com.example.myapplication.Update.EXTRA_REPLY_DATA)


            if (pid != null && ptitulo != null && ptexto != null && pdata != null) {
                val note = Note(id = pid, titulo = ptitulo, texto = ptexto, data = pdata)
                noteViewModel.deleteNote(note)
            }
            }   else {
                        Toast.makeText(
                                applicationContext,
                                "Não inseriu nenhuma nota",
                                Toast.LENGTH_LONG).show()
            }
    }

    override fun onClickListener(note: Note) {
        val intent = Intent(this, Update::class.java)

        intent.putExtra("id", note.id)
        intent.putExtra("titulo", note.titulo)
        intent.putExtra("texto", note.texto)
        intent.putExtra("data", note.data)

        startActivityForResult(intent, UpdateNoteActivityRequestCode)
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.apagartudo -> {
                noteViewModel.deleteAll()
                true
            }


            R.id.alterar -> {
                val note = Note(id = 1, titulo = "xxx", texto = "xxx", data = "xxx")
                noteViewModel.updateNote(note)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}