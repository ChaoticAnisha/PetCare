package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.an1ee.petcare.R
import com.an1ee.petcare.adapter.OrderAdapter
import com.an1ee.petcare.model.Order

class ProfileActivity : AppCompatActivity() {
    private lateinit var recyclerOrders: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        setupViews()
        setupNavigation()
        loadOrders()
    }

    private fun setupViews() {
        recyclerOrders = findViewById(R.id.recycler_orders)
        recyclerOrders.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter()
        recyclerOrders.adapter = orderAdapter
    }

    private fun setupNavigation() {
        findViewById<ImageView>(R.id.button_home).setOnClickListener {
            navigateToProductDashboard()
        }

        findViewById<ImageView>(R.id.button_cart).setOnClickListener {
            navigateToProductCart()
        }

        findViewById<ImageView>(R.id.button_notification).setOnClickListener {
        }
        findViewById<TextView>(R.id.my_order).setOnClickListener {
            navigateToOrder()
        }
    }
    private fun navigateToOrder() {
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToProductDashboard() {
        val intent = Intent(this, ProductDashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun navigateToProductCart() {
        startActivity(Intent(this, ProductCartActivity::class.java))
    }

    private fun loadOrders() {
        val orders = listOf(
            Order(
                id = "ORD001",
                date = "2024-02-24",
                total = 125.99,
                status = "Delivered",
                items = listOf("Pet Food", "Dog Toy")
            ),
            Order(
                id = "ORD002",
                date = "2024-02-20",
                total = 89.99,
                status = "In Transit",
                items = listOf("Cat Litter", "Cat Food")
            )
        )
        orderAdapter.submitList(orders)
    }
}