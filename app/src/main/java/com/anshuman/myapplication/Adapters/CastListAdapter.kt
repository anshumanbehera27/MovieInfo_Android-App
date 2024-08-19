package com.anshuman.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.myapplication.Models.Cast
import com.anshuman.myapplication.R
import com.bumptech.glide.Glide

class CastListAdapter(private val casts: MutableList<Cast>) : RecyclerView.Adapter<CastListAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.viewholder_actors, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cast = casts[position]
        Glide.with(context)
            .load(cast.PicUrl)
            .into(holder.pic)
        holder.nameTxt.text = cast.Actor
    }

    override fun getItemCount(): Int = casts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic: ImageView = itemView.findViewById(R.id.itemImage)
        val nameTxt: TextView = itemView.findViewById(R.id.nameTxt)
    }
}
