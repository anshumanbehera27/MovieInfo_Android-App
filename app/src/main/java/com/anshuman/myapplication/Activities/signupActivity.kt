package com.anshuman.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.myapplication.MainActivity
import com.anshuman.myapplication.R
import com.anshuman.myapplication.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set up click listener for Sign Up button
        binding.btnsignup.setOnClickListener {
            val user = binding.etusername.text.toString().trim()
            val email = binding.etemail.text.toString().trim()
            val password = binding.etpassword.text.toString().trim()

            // Check input fields
            when {
                user.isEmpty() -> binding.etusername.error = "Please enter username"
                email.isEmpty() -> binding.etemail.error = "Please enter email"
                password.isEmpty() -> binding.etpassword.error = "Please enter password"
                else -> {
                    // Create user with Firebase Authentication
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Account creation successful, redirect to MainActivity
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                // Account creation failed, display error
                                Toast.makeText(this, "Account Creation Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }

        // Redirect to login page when clicking "Login" TextView
        binding.tvlogin.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
    }
}
