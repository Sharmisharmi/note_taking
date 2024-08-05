package com.example.notetaking.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notetaking.view.NotesDetailsActivity
import com.example.notetaking.model.NotesGetData
import com.example.notetaking.R
import java.util.*

class NotesAdapter(context: Context, data: List<NotesGetData>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var context : Context
    var data:List<NotesGetData>


    init {
        this.context=context
        this.data= data!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.all_notes_list,parent,false))
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {

        holder.notesTitle.text=data[position].title.toString()
        holder.notesContent.text=data[position].content.toString()

        // Generate and set random background color
        val randomColor = getRandomColor()
        holder.notes_Card.setCardBackgroundColor(randomColor)

        holder.notes_Card.setOnClickListener {
            context.startActivity(Intent(context, NotesDetailsActivity::class.java).putExtra("notes_id",data[position].id.toInt()))
        }

    }
    fun getRandomColor(): Int {
        val random = Random()
        // Generate a lighter and pastel-like color
        val red = random.nextInt(150) + 100 // Range: 100-250
        val green = random.nextInt(150) + 100 // Range: 100-250
        val blue = random.nextInt(150) + 100 // Range: 100-250
        return Color.rgb(red, green, blue)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        var notesContent : TextView = itemView.findViewById(R.id.notes_content_tv)
        var notesTitle : TextView = itemView.findViewById(R.id.notes_title_tv)
        var notes_Card : CardView = itemView.findViewById(R.id.notes_ll)

    }


}