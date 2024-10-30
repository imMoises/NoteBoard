package com.example.noteapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class updateNoteMain : AppCompatActivity() {

    private lateinit var db : NotesDatabaseHelper
    private  var  noteId : Int = -1
    private  lateinit var tituloEditText : EditText
    private  lateinit var descripcionEditText : EditText
    private lateinit var btnActualizar : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_note_main)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)

        tituloEditText = findViewById(R.id.ETTitulo)
        descripcionEditText = findViewById(R.id.ETDescripcion)
        btnActualizar = findViewById(R.id.ActualizarNota)


        if(noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)

        tituloEditText.setText(note.title)
        descripcionEditText.setText(note.description)

        btnActualizar.setOnClickListener {
            val newTitle = tituloEditText.text.toString()
            val newDescription = descripcionEditText.text.toString()
            val updateNote = Note(noteId, newTitle, newDescription)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Nota Actualizada", Toast.LENGTH_SHORT).show()
        }







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}