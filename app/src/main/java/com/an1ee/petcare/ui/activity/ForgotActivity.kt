package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.an1ee.petcare.databinding.ActivityForgotBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ForgotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Reset button click
        binding.buttonReset.setOnClickListener {
            handlePasswordReset()
        }

        // Back to Login click
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
                setLoadingState(true)
                auth.sendPasswordResetEmail(email).await()

                Toast.makeText(
                    this@ForgotActivity,
                    "Password reset email sent to $email",
                    Toast.LENGTH_LONG
                ).show()

                // Redirect to login after success
                navigateToLogin()

            } catch (e: Exception) {
                Toast.makeText(
                    this@ForgotActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                setLoadingState(false)
            }
        }
    }

    private fun validateInput(email: String): Boolean {
        binding.editEmail.error = null

        return when {
            email.isEmpty() -> {
                binding.editEmail.error = "Email is required"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.editEmail.error = "Invalid email format"
                false
            }
            else -> true
        }
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
