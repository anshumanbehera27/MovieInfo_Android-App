package com.anshuman.myapplication.Fragments

import FilmListAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.anshuman.myapplication.Adapters.SlidersAdapter
import com.anshuman.myapplication.Models.Film
import com.anshuman.myapplication.Models.SliderItems
import com.anshuman.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()

        initBanner()
        initUpcoming()
        initTopMovies()

        return binding.root
    }

    private fun initBanner() {
        val myRef = database.getReference("Banners")
        binding.progressBarBanner.visibility = View.VISIBLE
        val items = mutableListOf<SliderItems>()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val snapshot = myRef.get().await()
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(SliderItems::class.java)?.let { items.add(it) }
                    }
                    setupBanners(items)
                } else {
                    Log.w("HomeFragment", "No banners found")
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching banners: ${e.message}")
            } finally {
                binding.progressBarBanner.visibility = View.GONE
            }
        }
    }

    private fun setupBanners(items: MutableList<SliderItems>) {
        if (items.isNotEmpty()) {
            binding.viewPager2.adapter = SlidersAdapter(items, binding.viewPager2)
            binding.viewPager2.clipToPadding = false
            binding.viewPager2.clipChildren = false
            binding.viewPager2.offscreenPageLimit = 3
            binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer { page, position ->
                    val r = 1 - Math.abs(position)
                    page.scaleY = 0.85f + r * 0.15f
                }
            }

            binding.viewPager2.setPageTransformer(compositePageTransformer)
            binding.viewPager2.setCurrentItem(0)

            viewLifecycleOwner.lifecycleScope.launch {
                while (true) {
                    delay(2000)
                    binding.viewPager2.currentItem = (binding.viewPager2.currentItem + 1) % (binding.viewPager2.adapter?.itemCount ?: 1)
                }
            }
        } else {
            Log.w("HomeFragment", "No banner items to display")
        }
    }

    private fun initUpcoming() {
        val myRef = database.getReference("Upcomming")
        binding.progressBarUpcoming.visibility = View.VISIBLE
        val items = mutableListOf<Film>()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val snapshot = myRef.get().await()
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        val movie = issue.getValue(Film::class.java)
                        movie?.let { items.add(it) }
                    }
                    if (items.isNotEmpty()) {
                        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewUpcoming.adapter = FilmListAdapter(items)
                    } else {
                        Log.w("HomeFragment", "No upcoming movie items to display")
                        binding.recyclerViewUpcoming.visibility = View.GONE
                    }
                } else {
                    Log.w("HomeFragment", "No upcoming items found")
                    binding.recyclerViewUpcoming.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching upcoming movies: ${e.message}")
            } finally {
                binding.progressBarUpcoming.visibility = View.GONE
            }
        }
    }

    private fun initTopMovies() {
        val myRef = database.getReference("Items")
        binding.progressBarTop.visibility = View.VISIBLE
        val items = mutableListOf<Film>()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val snapshot = myRef.get().await()
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        val movie = issue.getValue(Film::class.java)
                        movie?.let { items.add(it) }
                    }
                    if (items.isNotEmpty()) {
                        binding.recyclerViewTopMovies.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewTopMovies.adapter = FilmListAdapter(items)
                    } else {
                        Log.w("HomeFragment", "No top movie items to display")
                        binding.recyclerViewTopMovies.visibility = View.GONE
                    }
                } else {
                    Log.w("HomeFragment", "No top movie items found")
                    binding.recyclerViewTopMovies.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching top movies: ${e.message}")
            } finally {
                binding.progressBarTop.visibility = View.GONE
            }
        }
    }
}
