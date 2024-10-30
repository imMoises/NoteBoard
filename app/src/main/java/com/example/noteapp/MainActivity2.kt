package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noteapp.databinding.ActivityMain2Binding
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    private lateinit var dataBase: NotesDatabaseHelper
    private  lateinit var tituloEditText : EditText
    private  lateinit var descripcionEditText : EditText
    private lateinit var guardarImageView : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        tituloEditText = findViewById(R.id.ETTitulo)
        descripcionEditText = findViewById(R.id.ETDescripcion)
        guardarImageView = findViewById(R.id.IVguardarNota)

        guardarImageView.setOnClickListener{
            guardarNota()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun guardarNota(){
        val titulo = tituloEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()

        if(titulo.isEmpty() || descripcion.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = NotesDatabaseHelper(this)
        dbHelper.insertNote(titulo, descripcion)

        Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()

        finish()
    }
}