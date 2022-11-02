package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.model.Entity
import com.example.noteapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){
    val allNotes: LiveData<List<Entity>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.allNotes.asLiveData()

    }
    fun addNote(noteEntity: Entity) = viewModelScope.launch(Dispatchers.IO)
    {
        repository.insertNote(noteEntity)
    }

    fun updateNote(noteEntity: Entity) = viewModelScope.launch(Dispatchers.IO)
    {
        repository.updateNote(noteEntity)
    }


    fun deleteNote(noteEntity: Entity) = viewModelScope.launch(Dispatchers.IO)
    {
        repository.deleteNote(noteEntity)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Entity>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}