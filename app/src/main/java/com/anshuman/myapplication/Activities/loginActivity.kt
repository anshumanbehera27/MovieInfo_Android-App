package com.anshuman.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.myapplication.MainActivity
import com.anshuman.myapplication.databinding.ActivityLoginBinding

class loginActivity : AppCompatActivity() {
lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Reset error messages
        binding.etuser.error = null
        binding.etPassword.error = null
        // it will reditect to the main page
        // if it will alrady have an accout then we need to move to the home page
        binding.btnlogin.setOnClickListener{
            val usename = binding.etuser.text.toString()
            val password = binding.etPassword.text.toString()
            if (usename.isEmpty() ==true ){
              binding.etuser.setError("enter username")
            }
            else if (password.isEmpty() == true){
                binding.etPassword.setError("enter password")
            }
            else{
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
                Toast.makeText(this , "login successfully" , Toast.LENGTH_SHORT).show()
            }
        }
        // it will redrict to the Singup activity
        binding.tvregister.setOnClickListener{
            val intent = Intent(this , signupActivity::class.java)
            startActivity(intent)
        }


    }
}