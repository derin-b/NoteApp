package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.data.NoteDao
import com.example.noteapp.model.Entity
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Entity>> = noteDao.getAllNotes()

    suspend fun insertNote(noteEntity: Entity){
        noteDao.insert(noteEntity)
    }

    suspend fun updateNote(noteEntity: Entity){
        noteDao.update(noteEntity)
    }

    suspend fun deleteNote(noteEntity: Entity){
        noteDao.delete(noteEntity)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Entity>> {
        return noteDao.searchDatabase(searchQuery)
    }
}