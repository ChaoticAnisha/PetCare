package com.an1ee.petcare.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.an1ee.petcare.R
import com.an1ee.petcare.databinding.ActivityOrderBinding
import com.an1ee.petcare.adapter.OrderAdapter
import com.an1ee.petcare.model.Order

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        loadOrders()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "My Orders"
        }
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter()
        binding.recyclerOrders.apply {
            layoutManager = LinearLayoutManager(this@OrderActivity)
            adapter = orderAdapter
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}