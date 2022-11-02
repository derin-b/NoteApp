package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.databinding.ActivityNoteBinding
import com.example.noteapp.model.Entity
import com.example.noteapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class Note : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    lateinit var hex: String
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.tvSave.setOnClickListener {
            saveNote()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        hex = "#DEA44D"

        val palette = binding.colorPalette
        palette.setOnClickListener {
            binding.colorLayout.visibility = View.VISIBLE
            binding.colorPalette.visibility = View.GONE
            setBackgroundColor()
        }
    }

    private fun setBackgroundColor(){

        binding.lemon.setOnClickListener {
            colorNumber(1)
        }
        binding.yellow.setOnClickListener {
            colorNumber(2)
        }
        binding.lilac.setOnClickListener {
            colorNumber(3)
        }
        binding.peach.setOnClickListener {
            colorNumber(4)
        }
        binding.orange.setOnClickListener {
            colorNumber(5)
        }
        binding.darkBrown.setOnClickListener {
            colorNumber(6)
        }
        binding.brown.setOnClickListener {
            colorNumber(7)
        }
    }

    private fun colorNumber(view:Int){
        val layout = binding.root
        when(view){
            1->{
                layout.setBackgroundResource(R.color.lemon)
                hex = "#CBDB57"
            }
            2->{
                layout.setBackgroundResource(R.color.yellow)
                hex = "#DEA44D"
            }
            3->{
                layout.setBackgroundResource(R.color.lilac)
                hex = "#9585BA"
            }
            4->{
                layout.setBackgroundResource(R.color.peach)
                hex = "#F96A4B"
            }
            5->{
                layout.setBackgroundResource(R.color.orange)
                hex = "#FE9A37"

            }
            6->{
                layout.setBackgroundResource(R.color.dark_brown)
                hex = "#5C4F45"

            }
            7->{
                layout.setBackgroundResource(R.color.brown)
                hex = "#9E5C32"

            }

        }
        binding.colorLayout.visibility = View.GONE
        binding.colorPalette.visibility = View.VISIBLE

    }

    private fun saveNote() {
        val noteCalendar = Calendar.getInstance()
        val currentDate = noteCalendar.time
        val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val date = sdf.format(currentDate)

        val colour = hex

        val title = binding.etTitle.text.toString()
        val noteText = binding.etText.text.toString()

        if(title.isNotEmpty() && noteText.isNotEmpty() && date.isNotEmpty()){
            viewModel.addNote(Entity(title = title, noteText = noteText, date = date, color = colour))
            binding.etTitle.text?.clear()
            binding.etText.text?.clear()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Nothing to save; the note has been discarded",
                Toast.LENGTH_LONG).show()
        }
    }
}