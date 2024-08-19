package com.anshuman.myapplication.Activities

import AllmovieAdapter
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshuman.myapplication.Models.Film
import com.anshuman.myapplication.databinding.ActivityAllMovieBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AllMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllMovieBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRefUpcoming: DatabaseReference
    private lateinit var myRefTopMovies: DatabaseReference
    private lateinit var allmovieAdapter: AllmovieAdapter
    private val allMovies = mutableListOf<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        myRefUpcoming = database.getReference("Upcoming") // Correct the reference name if needed
        myRefTopMovies = database.getReference("Items")

        binding.allmovierecycleview.layoutManager = LinearLayoutManager(this)
        allmovieAdapter = AllmovieAdapter(allMovies, this) // Pass the context here
        binding.allmovierecycleview.adapter = allmovieAdapter

        fetchAndDisplayMovies()

        // Setup search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                allmovieAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                allmovieAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun fetchAndDisplayMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Fetch upcoming movies
                val upcomingSnapshot = myRefUpcoming.get().await()
                if (upcomingSnapshot.exists()) {
                    for (issue in upcomingSnapshot.children) {
                        val movie = issue.getValue(Film::class.java)
                        movie?.let { allMovies.add(it) }
                    }
                }

                // Fetch top movies
                val topMoviesSnapshot = myRefTopMovies.get().await()
                if (topMoviesSnapshot.exists()) {
                    for (issue in topMoviesSnapshot.children) {
                        val movie = issue.getValue(Film::class.java)
                        movie?.let { allMovies.add(it) }
                    }
                }

                // Update RecyclerView with all movies
                runOnUiThread {
                    if (allMovies.isNotEmpty()) {
                        allmovieAdapter.setData(allMovies)
                    } else {
                        Toast.makeText(this@AllMovieActivity, "No movies to display", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@AllMovieActivity, "Error fetching movies: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
