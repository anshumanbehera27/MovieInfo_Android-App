package com.anshuman.myapplication.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.anshuman.myapplication.Activities.DeatilsViewActivity
import com.anshuman.myapplication.Models.Film
import com.anshuman.myapplication.R
import java.util.Locale

class AllmovieAdapter(private var items: MutableList<Film>) :
    RecyclerView.Adapter<AllmovieAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context
    private var itemsFiltered: MutableList<Film> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.allmovie_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = itemsFiltered[position]
        holder.titleTxt.text = film.Title

        val requestOptions = RequestOptions().apply {
            transform(CenterCrop(), RoundedCorners(30))
        }

        Glide.with(context)
            .load(film.Poster)
            .apply(requestOptions)
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeatilsViewActivity::class.java).apply {
                putExtra("object", film)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemsFiltered.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.nameTxt)
        val pic: ImageView = itemView.findViewById(R.id.pic)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val query = charSequence?.toString()?.lowercase(Locale.getDefault())
                itemsFiltered = if (query.isNullOrEmpty()) {
                    items
                } else {
                    val filteredList = mutableListOf<Film>()
                    for (film in items) {
                        if (film.Title.lowercase(Locale.getDefault()).contains(query)) {
                            filteredList.add(film)
                        }
                    }
                    filteredList
                }
                return FilterResults().apply { values = itemsFiltered }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                itemsFiltered = filterResults?.values as MutableList<Film>
                notifyDataSetChanged()
            }
        }
    }

    fun setData(newItems: MutableList<Film>) {
        this.items = newItems
        this.itemsFiltered = newItems
        notifyDataSetChanged()
    }
}
