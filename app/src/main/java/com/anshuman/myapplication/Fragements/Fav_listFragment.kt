package com.anshuman.myapplication.Fragements

import AllmovieAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.myapplication.Models.Film
import com.anshuman.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Fav_listFragment : Fragment() {

    private val database = FirebaseDatabase.getInstance()
    private val favoritesRef = database.getReference("favorites")
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var recyclerViewFav: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fav_list, container, false)

        // Initialize Toolbar and RecyclerView
        toolbar = view.findViewById(R.id.toolbar)
        recyclerViewFav = view.findViewById(R.id.recyclerViewFav)

        toolbar.title = "Fav Movies"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setBackgroundColor(resources.getColor(R.color.dark_blue))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Toolbar
        toolbar.setNavigationOnClickListener {
            // Handle toolbar navigation click (if any)
        }

        // Fetch and display favorite movies
        fetchFavoriteMovies()
    }

    private fun fetchFavoriteMovies() {
        favoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteFilms = mutableListOf<Film>()
                for (child in snapshot.children) {
                    val film = child.getValue(Film::class.java)
                    film?.let { favoriteFilms.add(it) }
                }

                // Setup RecyclerView if the context is not null
                context?.let { ctx ->
                    recyclerViewFav.layoutManager = LinearLayoutManager(ctx)
                    recyclerViewFav.adapter = AllmovieAdapter(favoriteFilms, ctx)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
