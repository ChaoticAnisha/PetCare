package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.an1ee.petcare.R
import com.an1ee.petcare.databinding.ActivityLoginBinding
import com.an1ee.petcare.repository.UserRepositoryImpl
import com.an1ee.petcare.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel= UserViewModel(UserRepositoryImpl())
        setupClickListeners()
        setupWindowInsets()
    }

    private fun setupClickListeners() {
        binding.signUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }

        binding.buttonLogin.setOnClickListener {
            validateLogin()
    }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateLogin() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString()

        if (email.isEmpty()) {
            binding.editEmail.error = "Email is required"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmail.error = "Invalid email format"
            return
        }
        if (password.isEmpty()) {
            binding.editPassword.error = "Password is required"
            return
        }
        isauth(email,password)
    }

    private fun isauth(email: String, password: String) {
        userViewModel.login(email, password) { success, role ->
            if (success) {
                if (role == "admin") {
                    startActivity(Intent(this@LoginActivity, AdminDashboardActivity::class.java))
                } else {
                    startActivity(Intent(this@LoginActivity, ProductDashboardActivity::class.java))
                }
                finish()
            } else {
                Toast.makeText(this, "Login failed: $role", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveLoginStatus() {
        getSharedPreferences("PetCarePrefs", MODE_PRIVATE)
            .edit()
            .putBoolean("isLoggedIn", true)
            .apply()
    }
}