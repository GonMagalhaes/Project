package com.example.myapplication.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dataclasses.Notes
import kotlinx.android.synthetic.main.recyclerline.view.*


class LineAdapter(val list: ArrayList<Notes>):RecyclerView.Adapter<LineViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerline, parent, false);
        return LineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentPlace = list[position]

        holder.titulo.text = currentPlace.titulo
        holder.conteudo.text = currentPlace.conteudo
        holder.dia.text = currentPlace.dia.toString()
    }

}

class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val titulo = itemView.titulo
    val conteudo = itemView.conteudo
    var dia = itemView.dia

}

