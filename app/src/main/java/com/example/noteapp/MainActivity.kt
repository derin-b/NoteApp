package com.example.noteapp

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.adapter.NoteDeleteInterface
import com.example.noteapp.adapter.NoteUpdateInterface
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.Entity
import com.example.noteapp.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteDeleteInterface, NoteUpdateInterface{
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel

    private val noteAdapter: NoteAdapter by lazy { NoteAdapter(this, this, this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, Note::class.java)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL)

        binding.recyclerView.adapter = noteAdapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        viewModel.allNotes.observe(this, Observer { list->
            list?.let{
                noteAdapter.noteList(it)
            }
        })
    }

    override fun onDeleteIconClick(noteEntity: Entity) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Note")
        builder.setIcon(R.drawable.bin)
        builder.setMessage("Are you sure you want to delete?")

        builder.setPositiveButton("Yes"){ dialog, _ ->
            viewModel.deleteNote(noteEntity)
            Toast.makeText(this, "${noteEntity.title} Deleted", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("No"){ dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onUpdateClick(noteEntity: Entity) {
        val intent = Intent(this, Update::class.java)
        intent.putExtra("title", noteEntity.title)
        intent.putExtra("noteText", noteEntity.noteText)
        intent.putExtra("noteId", noteEntity.id)
        intent.putExtra("date", noteEntity.date)
        intent.putExtra("color", noteEntity.color)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val item = menu.findItem(R.id.menuSearch)
        val searchView = item.actionView as SearchView
        searchView.isSubmitButtonEnabled = true

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    searchDatabase(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null){
                    searchDatabase(query)
                }
                return true
            }

        })
        return true
    }
    private fun searchDatabase(query:String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this){list ->
            list.let{
                noteAdapter.noteList(it)
            }
        }
    }
}