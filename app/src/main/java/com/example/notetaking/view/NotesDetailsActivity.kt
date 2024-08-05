package com.example.notetaking.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notetaking.NotesDatabaseHelper
import com.example.notetaking.model.NotesGetData
import com.example.notetaking.databinding.ActivityNotesDetailsBinding

class NotesDetailsActivity : AppCompatActivity() {

    lateinit var binding:ActivityNotesDetailsBinding

    lateinit var db : NotesDatabaseHelper
    var notes_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityNotesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        notes_id = intent.getIntExtra("notes_id",0)

        if (notes_id == 0){
            finish()
            return
        }


            val note = db.getNoteById(notes_id)
        binding.titleEt.setText(note!!.title.toString())
        binding.contentEt.setText(note!!.content.toString())

        binding.saveCard.setOnClickListener {
            if (binding.titleEt.text.toString().isNullOrEmpty()){
                Toast.makeText(this,"Enter Tile",Toast.LENGTH_SHORT).show()
            }else if(binding.contentEt.text.toString().isNullOrEmpty()){
                Toast.makeText(this,"Enter Content",Toast.LENGTH_SHORT).show()
            }else {
                db.updateNotes(
                    NotesGetData(
                        notes_id,
                        binding.titleEt.text.toString(),
                        binding.contentEt.text.toString()
                    )
                )
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Changes Updated", Toast.LENGTH_SHORT).show()
            }
        }


        binding.deleteIv.setOnClickListener {
            db.deleteNoteById(notes_id)
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this,"Note Deleted",Toast.LENGTH_SHORT).show()
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