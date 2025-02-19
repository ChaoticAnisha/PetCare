package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.an1ee.petcare.R
import com.an1ee.petcare.model.CartItem
import com.an1ee.petcare.utils.CartManager

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Get views
        val productImage = findViewById<ImageView>(R.id.image_product)
        val productName = findViewById<TextView>(R.id.text_product_name)
        val productDescription = findViewById<TextView>(R.id.text_product_description)
        val productPrice = findViewById<TextView>(R.id.text_product_price)
        val addToCartButton = findViewById<Button>(R.id.button_add_to_cart)

        // Navigation buttons
        val buttonHome = findViewById<ImageView>(R.id.button_home)
        val buttonProfile = findViewById<ImageView>(R.id.button_profile)
        val buttonCart = findViewById<ImageView>(R.id.button_cart)

        // Get data from intent
        val name = intent.getStringExtra("productName") ?: "Product Name"
        val priceStr = intent.getStringExtra("productPrice") ?: "$0.00"
        val description = intent.getStringExtra("productDescription") ?: "No description available"
        val imageResource = intent.getIntExtra("productImage", R.drawable.food)

        // Parse price (remove currency symbol and convert to double)
        val price = priceStr.replace("$", "").toDoubleOrNull() ?: 0.0

        // Set data to views
        productImage.setImageResource(imageResource)
        productName.text = name
        productDescription.text = description
        productPrice.text = priceStr

        // Add to cart button click listener
        addToCartButton.setOnClickListener {
            val cartItem = CartItem(
                id = name.hashCode(), // Using name as unique identifier
                name = name,
                price = price,
                description = description,
                imageResource = imageResource
            )
            CartManager.addItem(cartItem)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
        }

        // Navigation click listeners
        buttonHome.setOnClickListener {
            val intent = Intent(this, ProductDashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        buttonProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        buttonCart.setOnClickListener {
            startActivity(Intent(this, ProductCartActivity::class.java))
        }
    }
}