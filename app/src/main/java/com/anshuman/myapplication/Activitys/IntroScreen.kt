package com.anshuman.myapplication.Activitys

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anshuman.myapplication.MainActivity
import com.anshuman.myapplication.R
import com.anshuman.myapplication.databinding.ActivityIntroScreenBinding

class IntroScreen : AppCompatActivity() {
    lateinit var binding: ActivityIntroScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)
        // By clicking this it will reDrict to the  LoginPage
        binding.btngetstrated.setOnClickListener{
            val  intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }

    }
}