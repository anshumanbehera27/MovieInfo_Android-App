package com.anshuman.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.anshuman.myapplication.Models.SliderItems
import com.anshuman.myapplication.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class SlidersAdapter(
    private val sliderItems: MutableList<SliderItems>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SlidersAdapter.SliderViewHolder>() {

    private lateinit var context: Context

    private val runnable = Runnable {
        val newItems = ArrayList(sliderItems) // Create a copy of the current items
        sliderItems.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.slider_viewholder, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        // implementing infinite scrolling or looping.
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)
        private val nameTxt: TextView = itemView.findViewById(R.id.nameTxt)
        private val genreTxt: TextView = itemView.findViewById(R.id.genreTxt)
        private val ageTxt: TextView = itemView.findViewById(R.id.ageTxt)
        private val yearTxt: TextView = itemView.findViewById(R.id.yearTxt)
        private val timeTxt: TextView = itemView.findViewById(R.id.timeTxt)

        fun setImage(sliderItems: SliderItems) {
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(60))
            Glide.with(context)
                .load(sliderItems.image)
                .apply(requestOptions)
                .into(imageView)

            nameTxt.text = sliderItems.name
            genreTxt.text = sliderItems.genre
            ageTxt.text = sliderItems.age
            yearTxt.text = sliderItems.year
            timeTxt.text = sliderItems.time
        }
    }
}