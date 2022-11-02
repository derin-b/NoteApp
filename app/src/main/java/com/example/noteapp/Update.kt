package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.databinding.ActivityUpdateBinding
import com.example.noteapp.model.Entity
import com.example.noteapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class Update : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    lateinit var viewModel : NoteViewModel
    private var noteId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteTitle = intent.getStringExtra("title")
        val noteText = intent.getStringExtra("noteText")
        val noteDate = intent.getStringExtra("date")
        noteId = intent.getIntExtra("noteId", 0)
        val noteColour = intent.getStringExtra("color")


        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        binding.etUpdateTitle.setText(noteTitle)
        binding.etUpdateText.setText(noteText)
        binding.Date.text = noteDate

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.tvSave.setOnClickListener {
            val noteCalendar = Calendar.getInstance()
            val currentDate = noteCalendar.time
            val sdf = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
            val date = sdf.format(currentDate)

            val colour = noteColour.toString()
            val id = noteId

            val title = binding.etUpdateTitle.text.toString()
            val text = binding.etUpdateText.text.toString()
            binding.Date.text = date

            if(title.isNotEmpty() && text.isNotEmpty() && date.isNotEmpty()){
                viewModel.updateNote(Entity(id = id,title= title, noteText = text, date = date, color = colour))
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}