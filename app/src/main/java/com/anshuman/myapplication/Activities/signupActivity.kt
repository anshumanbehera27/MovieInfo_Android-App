package com.anshuman.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anshuman.myapplication.MainActivity
import com.anshuman.myapplication.R
import com.anshuman.myapplication.databinding.ActivitySignupBinding

class signupActivity : AppCompatActivity(){

    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Reset error messages
        binding.etusername.error = null
        binding.etemail.error = null
        binding.etpassword.error = null

        // it will reditect to the signup page
        binding.btnsignup.setOnClickListener {
            val user = binding.etusername.text.toString().trim()
            val email = binding.etemail.text.toString().trim()
            val password = binding.etpassword.text.toString().trim()
            if (user.isEmpty()) {
                binding.etusername.error = "Please enter username"

            }
            else if (email.isEmpty()) {
                binding.etemail.error = "Please enter email"

            }
            else if (password.isEmpty()) {
                binding.etpassword.error = "Please enter password"

            }
            else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this , "Account Created Successfully" , Toast.LENGTH_SHORT).show()

            }
        }
        // it will redrict to the login page
        binding.tvlogin.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }


    }
}