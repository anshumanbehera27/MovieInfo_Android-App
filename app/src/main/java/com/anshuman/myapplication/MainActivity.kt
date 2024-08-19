package com.anshuman.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anshuman.myapplication.Fragements.Fav_listFragment

import com.anshuman.myapplication.Fragements.ProfileFragment
import com.anshuman.myapplication.Fragments.HomeFragment
import com.anshuman.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*
      How to Define the Bottom Navigation
          todo 1  Link with the Bottom navigation view
          todo 2 - how the all the Fragemet will replace
          todo 3 - set on item select listiner
          todo 4 - define the defult fragement
        */
        val bottomNavigationView = binding.bottomNavigation

        // load defult home one
        replaceFragment(HomeFragment())

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())

                    true
                }
                R.id.favorites -> {
                    replaceFragment(Fav_listFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

}