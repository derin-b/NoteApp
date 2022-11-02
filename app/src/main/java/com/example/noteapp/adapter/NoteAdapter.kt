package com.example.noteapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.RecyclerViewBinding
import com.example.noteapp.model.Entity

class NoteAdapter(context: Context,
                  private val noteDeleteInterface: NoteDeleteInterface,
                  private val noteUpdateInterface: NoteUpdateInterface
)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val notes = arrayListOf<Entity>()

    inner class ViewHolder(binding: RecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
        val cardView = binding.cardView
        val title = binding.tvTitle
        val textContent = binding.tvText
        val date = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =RecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        val color = note.color
        holder.title.text = note.title
        holder.textContent.text = note.noteText
        holder.date.text = note.date

        holder.cardView.setOnLongClickListener {
            noteDeleteInterface.onDeleteIconClick(note)
            true
        }
        holder.cardView.setOnClickListener {
            noteUpdateInterface.onUpdateClick(note)
        }

        when(color){
            "#CBDB57" -> holder.cardView.setCardBackgroundColor(Color.parseColor("#CBDB57"))
            "#9585BA" -> holder.cardView.setCardBackgroundColor(Color.parseColor("#9585BA"))
            "#DEA44D" -> holder.cardView.setCardBackgroundColor(Color.parseColor("#DEA44D"))
            "#FE9A37" -> holder.cardView.setCardBackgroundColor(Color.parseColor("#FE9A37"))
            "#F96A4B" -> holder.cardView.setCardBackgroundColor(Color.parseColor("#F96A4B"))
            "#9E5C32" -> holder.cardView.setCardBackgroundColor(Color.parseColor("#9E5C32"))
            "#5C4F45" -> holder.cardView.setCardBackgroundColor(Color.parseColor("#5C4F45"))
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun noteList(newList: List<Entity>){
        notes.clear()
        notes.addAll(newList)
        notifyDataSetChanged()
    }
}
interface NoteDeleteInterface{
    fun onDeleteIconClick(noteEntity: Entity)
}
interface NoteUpdateInterface{
    fun onUpdateClick(noteEntity: Entity)
}