package com.anshuman.myapplication.Activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.anshuman.myapplication.Adapters.CastListAdapter
import com.anshuman.myapplication.Adapters.CategoryEachFilmAdapter
import com.anshuman.myapplication.Models.Film
import com.anshuman.myapplication.databinding.ActivityDeatilsViewBinding

import eightbitlab.com.blurview.RenderScriptBlur

class DeatilsViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeatilsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeatilsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVariable()

        // Set window flags for layout
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setVariable() {
        // Retrieve the Film object passed through the Intent
        val item = intent.getSerializableExtra("object") as? Film ?: return

        // Apply image loading and transformation with Glide
        val requestOptions = RequestOptions()
            .transform(CenterCrop(), GranularRoundedCorners(0f, 0f, 50f, 50f))

        Glide.with(this)
            .load(item.Poster)
            .apply(requestOptions)
            .into(binding.filmPic)

        // Set text and details
        binding.titleTxt.text = item.Title
        binding.imdbTxt.text = "IMDB ${item.Imdb}"
        binding.movieTimesTxt.text = "${item.Year} - ${item.Time}"
        binding.movieSummery.text = item.Description

        // Set up trailer button click listener
        binding.watchTrailerBtn.setOnClickListener {
            val id = item.Trailer.replace("https://www.youtube.com/watch?v=", "")
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.Trailer))

            try {
                startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                startActivity(webIntent)
            }
        }

        // Set up back button click listener
        binding.backImg.setOnClickListener { finish() }

        // Set up BlurView for background blur effect
        val decorView = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground: Drawable = decorView.background

        binding.blurView.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(10f)
        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        // Set up RecyclerView for genre if available
        item.Genre?.let {
            binding.genreView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.genreView.adapter = CategoryEachFilmAdapter(it)
        }

        // Set up RecyclerView for cast if available
        item.Casts?.let {
            binding.CastView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.CastView.adapter = CastListAdapter(it.toMutableList())
        }
    }
}
