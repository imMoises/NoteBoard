package com.example.noteapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class notesAdapter(private var notesList : List<Note>, context: Context) : RecyclerView.Adapter<notesAdapter.noteViewHolder>(){

    private val db: NotesDatabaseHelper =  NotesDatabaseHelper(context)

    class noteViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val titleNote : TextView = itemView.findViewById(R.id.tvTitle)
        val descriptionNote : TextView = itemView.findViewById(R.id.tvDescription)
        val updateNote : ConstraintLayout = itemView.findViewById(R.id.clNote)
        val deleteBtn : ImageButton = itemView.findViewById(R.id.deleteButon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note, parent, false)
        return noteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        val item = notesList[position]
        holder.titleNote.text = item.title
        holder.descriptionNote.text = item.description

        holder.updateNote.setOnClickListener {
            val intent = Intent(holder.itemView.context, updateNoteMain::class.java).apply {
                putExtra("note_id", item.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteBtn.setOnClickListener{
            db.deleteNote(item.id)
            updtateNotes(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Nota Eliminada", Toast.LENGTH_SHORT).show()


        }
    }

    fun updtateNotes(newNotes: List<Note>){
        this.notesList = newNotes
        notifyDataSetChanged()
    }

}