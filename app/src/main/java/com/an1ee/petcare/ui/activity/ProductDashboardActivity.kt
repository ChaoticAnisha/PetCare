package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.an1ee.petcare.R
import com.an1ee.petcare.databinding.ActivityProductDashboardBinding

class ProductDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupClickListeners()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            cardFood.setOnClickListener {
                startActivity(Intent(this@ProductDashboardActivity, FoodActivity::class.java))
            }

            cardToys.setOnClickListener {
                startActivity(Intent(this@ProductDashboardActivity, ToyActivity::class.java))
            }

            cardGrooming.setOnClickListener {
                startActivity(Intent(this@ProductDashboardActivity, GroomingActivity::class.java))
            }

            buttonProfile.setOnClickListener {
                startActivity(Intent(this@ProductDashboardActivity, ProfileActivity::class.java))
            }

            buttonHome.setOnClickListener {
                val intent = Intent(this@ProductDashboardActivity, ProductDashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
}