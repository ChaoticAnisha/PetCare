package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.an1ee.petcare.R
import com.an1ee.petcare.adapter.CartAdapter
import com.an1ee.petcare.model.Order
import com.an1ee.petcare.utils.CartManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductCartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var textSubtotal: TextView
    private lateinit var textDelivery: TextView
    private lateinit var textTax: TextView
    private lateinit var textTotal: TextView
    private lateinit var buttonPlaceOrder: Button
    private lateinit var textBack: TextView

    private var sourceActivity: String? = null

    private val cartUpdateListener: () -> Unit = {
        updateCartDisplay()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_cart)

        sourceActivity = intent.getStringExtra("source")

        // Initialize views
        recyclerView = findViewById(R.id.recycler_cart_items)
        textSubtotal = findViewById(R.id.text_subtotal)
        textDelivery = findViewById(R.id.text_delivery)
        textTax = findViewById(R.id.text_tax)
        textTotal = findViewById(R.id.text_total)
        buttonPlaceOrder = findViewById(R.id.button_place_order)
        textBack = findViewById(R.id.text_back)

        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(CartManager.getItems().toMutableList()) { itemId, quantity ->
            CartManager.updateQuantity(itemId, quantity)
        }
        recyclerView.adapter = cartAdapter

        // Add cart update listener
        CartManager.addListener(cartUpdateListener)

        // Initial update
        updateCartDisplay()

        // Handle Back Button Click
        textBack.setOnClickListener {
            navigateBackToSource()
        }

        // Place order button click listener
        buttonPlaceOrder.setOnClickListener {
            if (CartManager.getItems().isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showOrderConfirmationDialog()
        }
    }

    private fun showOrderConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Order")
            .setMessage("Are you sure you want to place this order?")
            .setPositiveButton("Yes") { _, _ ->
                placeOrder()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun placeOrder() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val order = Order(
            id = "ORD" + System.currentTimeMillis().toString().takeLast(6),
            date = dateFormat.format(Date()),
            total = CartManager.getTotal(),
            status = "Pending",
            items = CartManager.getItems().map { it.name }
        )

        AlertDialog.Builder(this)
            .setTitle("Order Placed Successfully!")
            .setMessage("Your order ID is: ${order.id}\nThank you for shopping with us!")
            .setPositiveButton("View Orders") { _, _ ->

                startActivity(Intent(this, OrderActivity::class.java))
                finish()
            }
            .setNegativeButton("Continue Shopping") { _, _ ->

                startActivity(Intent(this, ProductDashboardActivity::class.java))
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun updateCartDisplay() {
        cartAdapter.updateItems(CartManager.getItems())

        val subtotal = CartManager.getTotal()
        val delivery = 5.00 // Fixed delivery fee
        val tax = subtotal * 0.1 // 10% tax
        val total = subtotal + delivery + tax

        textSubtotal.text = String.format("$%.2f", subtotal)
        textDelivery.text = String.format("$%.2f", delivery)
        textTax.text = String.format("$%.2f", tax)
        textTotal.text = String.format("$%.2f", total)
    }

    private fun navigateBackToSource() {
        val intent = when (sourceActivity) {
            "Food" -> Intent(this, FoodActivity::class.java)
            "Toy" -> Intent(this, ToyActivity::class.java)
            "Grooming" -> Intent(this, GroomingActivity::class.java)
            else -> null
        }

        if (intent != null) {
            startActivity(intent)
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        CartManager.removeListener(cartUpdateListener)
    }
}