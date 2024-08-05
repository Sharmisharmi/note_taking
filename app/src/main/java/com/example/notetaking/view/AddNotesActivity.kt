package com.example.notetaking.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notetaking.NotesDatabaseHelper
import com.example.notetaking.model.NotesGetData
import com.example.notetaking.databinding.ActivityAddNotesBinding

class AddNotesActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNotesBinding
    lateinit var db : NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        binding.saveCard.setOnClickListener {
            var notes_title = binding.titleEt.text.toString()
            var notes_content = binding.contentEt.text.toString()

            if (notes_title.isNullOrEmpty()){
                Toast.makeText(this,"Enter Tile",Toast.LENGTH_SHORT).show()
            }else if(notes_content.isNullOrEmpty()){
                Toast.makeText(this,"Enter Content",Toast.LENGTH_SHORT).show()
            }else {
                val notes = NotesGetData(0, notes_title, notes_content)
                db.addNotes(notes)
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Notes Saved", Toast.LENGTH_SHORT).show()
            }

        }

        binding.backIv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))

    }
}