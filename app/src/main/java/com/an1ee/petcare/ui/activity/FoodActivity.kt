package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.an1ee.petcare.R

class FoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_food)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonProfile = findViewById<ImageView>(R.id.button_profile)
        val buttonHome = findViewById<ImageView>(R.id.button_home)
        val buttonCart = findViewById<ImageView>(R.id.button_cart)

        val productCards = listOf(
            findViewById<CardView>(R.id.card_sticks),
            findViewById<CardView>(R.id.card_cake),
            findViewById<CardView>(R.id.card_bone),
            findViewById<CardView>(R.id.card_milkbars),
            findViewById<CardView>(R.id.card_meat),
            findViewById<CardView>(R.id.card_mix)
        )

        val productData = listOf(
            ProductData("Sticks", "$49.99", "Premium quality dog food sticks", R.drawable.food1),
            ProductData("Cake", "$59.99", "Delicious dog cake treats", R.drawable.food6),
            ProductData("Bone", "$39.99", "Nutritious bone treats", R.drawable.food3),
            ProductData("Milkbars", "$39.99", "Calcium-rich milk bars", R.drawable.food4),
            ProductData("Meat", "$39.99", "Fresh meat dog food", R.drawable.food5),
            ProductData("Mix", "$39.99", "Mixed dog food variety", R.drawable.food2)
        )

        productCards.forEachIndexed { index, card ->
            card.setOnClickListener {
                val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                    putExtra("productName", productData[index].name)
                    putExtra("productPrice", productData[index].price)
                    putExtra("productDescription", productData[index].description)
                    putExtra("productImage", productData[index].imageResource)
                }
                startActivity(intent)
            }
        }

        buttonProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        buttonHome.setOnClickListener {
            val intent = Intent(this, ProductDashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        buttonCart.setOnClickListener {
        }
    }
}

data class ProductData(
    val name: String,
    val price: String,
    val description: String,
    val imageResource: Int
)