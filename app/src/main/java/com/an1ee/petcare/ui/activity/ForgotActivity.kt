package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.an1ee.petcare.databinding.ActivityForgotBinding
import com.an1ee.petcare.repository.UserRepositoryImpl
import com.an1ee.petcare.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ForgotActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityForgotBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        userViewModel = UserViewModel(UserRepositoryImpl())

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Handle Reset button click
        binding.buttonReset.setOnClickListener {
            handlePasswordReset()
        }

        // Handle Back to Login click
        binding.login.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun handlePasswordReset() {
        val email = binding.editEmail.text.toString().trim()

        if (!validateInput(email)) {
            return
        }

        lifecycleScope.launch {
            try {
                // Show loading state
                setLoadingState(true)

                // Send password reset email
                auth.sendPasswordResetEmail(email).await()

                // Show success message
                Toast.makeText(
                    this@ForgotActivity,
                    "Password reset email sent to $email",
                    Toast.LENGTH_LONG
                ).show()

                // Navigate back to login after short delay
                android.os.Handler(mainLooper).postDelayed({
                    navigateToLogin()
                }, 2000)

            } catch (e: Exception) {
                // Handle errors
                Toast.makeText(
                    this@ForgotActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                // Hide loading state
                setLoadingState(false)
            }
        }
    }

    private fun validateInput(email: String): Boolean {
        // Clear previous error
        binding.editEmail.error = null

        when {
            email.isEmpty() -> {
                binding.editEmail.error = "Email is required"
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.editEmail.error = "Invalid email format"
                return false
            }
        }
        return true
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.buttonReset.isEnabled = !isLoading
        binding.buttonReset.text = if (isLoading) "Sending..." else "Reset"
        binding.editEmail.isEnabled = !isLoading
        binding.login.isEnabled = !isLoading
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}