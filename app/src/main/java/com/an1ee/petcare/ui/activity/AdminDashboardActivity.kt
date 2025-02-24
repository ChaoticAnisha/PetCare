package com.an1ee.petcare.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.an1ee.petcare.R
import com.an1ee.petcare.ui.fragment.OrdersFragment
import com.an1ee.petcare.ui.fragment.ProductsFragment
import com.an1ee.petcare.ui.fragment.UsersFragment
import com.an1ee.petcare.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(OrdersFragment())

        binding.buttonOrders.setOnClickListener {
            Log.d("AdminDashboard", "Orders button clicked")
            loadFragment(OrdersFragment())
        }

        binding.buttonProducts.setOnClickListener {
            Log.d("AdminDashboard", "Products button clicked")
            loadFragment(ProductsFragment())
        }

        binding.buttonUsers.setOnClickListener {
            Log.d("AdminDashboard", "Users button clicked")
            loadFragment(UsersFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
