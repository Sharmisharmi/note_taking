package com.example.notetaking

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notetaking.model.NotesGetData

class NotesDatabaseHelper(context: Context):SQLiteOpenHelper(context, DB_Name,null, DB_Version) {

    companion object{
        private const val DB_Name = "notesapp.db"
        private const val DB_Version = 1
        private const val Table_Name = "allnotes"
        private const val Column_Id = "Id"
        private const val Column_Title = "Title"
        private const val Column_Content = "Content"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $Table_Name ($Column_Id INTEGER PRIMARY KEY, $Column_Title TEXT, $Column_Content TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS  $Table_Name")
        onCreate(db)
    }

    fun addNotes(notes : NotesGetData){
        val db = writableDatabase
        val vales= ContentValues().apply{
            put(Column_Title,notes.title)
            put(Column_Content,notes.content)
        }
        db.insert(Table_Name,null,vales)
        db.close()
    }


    // Method to update a note
    fun updateNotes(note: NotesGetData): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Column_Title, note.title)
            put(Column_Content, note.content)
        }

        return db.update(Table_Name, values, "$Column_Id=?", arrayOf(note.id.toString()))
    }


    // Method to delete a note by ID
    fun deleteNoteById(noteId: Int): Int {
        val db = writableDatabase
        val whereClause = "$Column_Id = ?"
        val whereArgs = arrayOf(noteId.toString())
        return db.delete(Table_Name, whereClause, whereArgs)
    }

    // Method to search notes by title or content
    fun searchNotes(query: String): List<NotesGetData> {
        val notesList = mutableListOf<NotesGetData>()
        val db = readableDatabase
        val selection = "$Column_Title LIKE ? OR $Column_Content LIKE ?"
        val selectionArgs = arrayOf("%$query%", "%$query%")

        val cursor = db.query(
            Table_Name,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(Column_Id))
                val title = it.getString(it.getColumnIndex(Column_Title))
                val content = it.getString(it.getColumnIndex(Column_Content))
                val note = NotesGetData(id, title, content)
                notesList.add(note)
            }
        }

        return notesList
    }
    // Method to retrieve a note by ID
    fun getNoteById(noteId: Int): NotesGetData? {
        val db = readableDatabase
        val selection = "$Column_Id = ?"
        val selectionArgs = arrayOf(noteId.toString())

        val cursor = db.query(
            Table_Name,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var note: NotesGetData? = null

        cursor.use {
            if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndex(Column_Id))
                val title = it.getString(it.getColumnIndex(Column_Title))
                val content = it.getString(it.getColumnIndex(Column_Content))
                note = NotesGetData(id, title, content)
            }
        }

        return note
    }
    fun getAllNotes(): List<NotesGetData> {
        val notesList = mutableListOf<NotesGetData>()
        val db = readableDatabase
        val query = "SELECT * FROM $Table_Name"
        val cursor = db.rawQuery(query, null)

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(Column_Id))
                val title = it.getString(it.getColumnIndex(Column_Title))
                val content = it.getString(it.getColumnIndex(Column_Content))
                val note = NotesGetData(id, title, content)
                notesList.add(note)
            }
        }

        return notesList
    }
}