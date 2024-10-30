package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class NotesDatabaseHelper (context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
){

    companion object{
        private const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val sqlCreateTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_DESCRIPTION TEXT" +
                ");"
        p0?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(sqlDropTable)
        onCreate(p0)
    }

    fun insertNote (titulo: String, descripcion: String):Long{
        val dataBase = writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_TITLE, titulo)
        contentValues.put(COLUMN_DESCRIPTION, descripcion)

        return dataBase.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val database = this.readableDatabase
        val sqlSelect = "SELECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(sqlSelect, null)

        if(cursor.moveToFirst()){
            do {
                val note = Note (
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                )
                notesList.add(note)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return  notesList

    }

    fun updateNote (note:Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DESCRIPTION, note.description)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getNoteByID(noteId: Int): Note{
        val db = readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()

        return Note(id, title, descripcion)

    }

    fun deleteNote (noteId : Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

}