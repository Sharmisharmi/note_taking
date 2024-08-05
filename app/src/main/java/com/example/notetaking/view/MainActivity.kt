package com.example.notetaking.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetaking.NotesDatabaseHelper
import com.example.notetaking.model.NotesGetData
import com.example.notetaking.adapter.NotesAdapter
import com.example.notetaking.databinding.ActivityAddNotesBinding
import com.example.notetaking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    var backpress = 0
    private var doubleBackToExitPressedOnce = false


    lateinit var db : NotesDatabaseHelper

    var notesList:List<NotesGetData> = ArrayList<NotesGetData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
         notesList = db.getAllNotes()
        getAllNotes()

        binding.typeWriterView.animateText("Your Note is Empty");
        binding.typeWriterView.avoidTextOverflowAtEdge(true)
        binding.typeWriterView.setCharacterDelay(100)//in ms

        binding.addLl.setOnClickListener {
            startActivity(Intent(this, AddNotesActivity::class.java))
        }

        binding.listView.setOnClickListener {
            if (notesList.size>0) {
                binding.listView.visibility = View.GONE
                binding.gridView.visibility = View.VISIBLE
                binding.notesRecyclerView.setHasFixedSize(true)
                binding.notesRecyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.notesRecyclerView.adapter = NotesAdapter(this, notesList)
            }
        }

        binding.gridView.setOnClickListener {
            if (notesList.size>0) {
                binding.listView.visibility = View.VISIBLE
                binding.gridView.visibility = View.GONE
                binding.notesRecyclerView.setHasFixedSize(true)
                binding.notesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                binding.notesRecyclerView.adapter = NotesAdapter(this, notesList)
            }
        }
        binding.searchIv.setOnClickListener {
            binding.addLl.visibility = View.GONE
            binding.notesRecyclerView.visibility = View.GONE
            binding.titleLl.visibility = View.GONE
            binding.gridView.visibility = View.GONE
            binding.listView.visibility = View.GONE
            binding.searchLl.visibility = View.VISIBLE
            backpress = -1
        }

        binding.backLl.setOnClickListener {
            backpress=0
            binding.addLl.visibility = View.VISIBLE
            binding.notesRecyclerView.visibility = View.VISIBLE
            binding.titleLl.visibility = View.VISIBLE
            binding.gridView.visibility = View.GONE
            binding.listView.visibility = View.VISIBLE
            binding.searchLl.visibility = View.GONE
            binding.searchNotesRecyclerView.visibility = View.GONE
            getAllNotes()
        }

        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that the text is about to change.
                // You can use it to take action before the text changes.
                binding.notesRecyclerView.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that the text has changed.
                // You can use it to perform operations based on the new text.
                Log.d("MainActivity", "Text changed: $s")
                val searchResults = db.searchNotes(s.toString())
                getSearchNotes(searchResults)

            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called to notify you that the text has been changed.
                // You can use it to perform actions after the text has changed.
                // For example, perform validation or trigger another action.

                val searchResults = db.searchNotes(s.toString())
                getSearchNotes(searchResults)
            }
        })

    }

    private fun getSearchNotes(searchResults: List<NotesGetData>) {
        if (searchResults.size>0){
            binding.searchNotesRecyclerView.setHasFixedSize(true)
            binding.searchNotesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            binding.searchNotesRecyclerView.adapter = NotesAdapter(this, searchResults)
        }
    }

    private fun getAllNotes() {
        val notesList = db.getAllNotes()
        if (notesList.size>0){
        binding.notesRecyclerView.visibility= View.VISIBLE
        binding.typeWriterView.visibility= View.GONE
        binding.notesRecyclerView.setHasFixedSize(true)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.notesRecyclerView.adapter = NotesAdapter(this, notesList)
    }else{
            binding.typeWriterView.visibility= View.VISIBLE
            binding.listView.visibility= View.GONE

        }

    }


    override fun onBackPressed() {

        if (backpress == -1){
            binding.addLl.visibility = View.VISIBLE
            binding.notesRecyclerView.visibility = View.VISIBLE
            binding.titleLl.visibility = View.VISIBLE
            binding.searchLl.visibility = View.GONE
            binding.searchNotesRecyclerView.visibility = View.GONE
            backpress=0
        }else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }

}