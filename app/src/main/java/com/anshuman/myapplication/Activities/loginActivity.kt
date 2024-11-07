package com.anshuman.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.myapplication.MainActivity
import com.anshuman.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Clear any error messages on startup
        binding.etuser.error = null
        binding.etPassword.error = null

        // Set up click listener for login button
        binding.btnlogin.setOnClickListener {
            val email = binding.etuser.text.toString().trim() // Updated variable name for clarity
            val password = binding.etPassword.text.toString().trim()

            // Validate fields
            when {
                email.isEmpty() -> binding.etuser.error = "Please enter email"
                password.isEmpty() -> binding.etPassword.error = "Please enter password"
                else -> {
                    // Attempt to sign in with Firebase
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign-in successful; navigate to MainActivity
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            } else {
                                // Sign-in failed; display error message
                                Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }

        // Redirect to SignupActivity when clicking "Register" TextView
        binding.tvregister.setOnClickListener {
            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }
    }
}
