package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.an1ee.petcare.R
import com.an1ee.petcare.databinding.ActivityRegisterBinding
import com.an1ee.petcare.model.UserModel
import com.an1ee.petcare.repository.UserRepositoryImpl
import com.an1ee.petcare.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var model:UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel= UserViewModel(UserRepositoryImpl())
        setupClickListeners()
        setupWindowInsets()
    }

    private fun setupClickListeners() {
        binding.login.setOnClickListener {
            finish()
        }

        binding.buttonRegister.setOnClickListener {
            if (validateForm()) {
                saveLoginStatus()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateForm(): Boolean {
        val fullName = binding.editFullName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString()

        if (fullName.isEmpty()) {
            binding.editFullName.error = "Full name is required"
            return false
        }

        if (email.isEmpty()) {
            binding.editEmail.error = "Email is required"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmail.error = "Invalid email format"
            return false
        }

        if (password.isEmpty()) {
            binding.editPassword.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            binding.editPassword.error = "Password must be at least 6 characters"
            return false
        }



        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun saveLoginStatus() {
        getSharedPreferences("PetCarePrefs", MODE_PRIVATE)
            .edit()
            .putBoolean("isLoggedIn", true)
            .apply()
    }
}