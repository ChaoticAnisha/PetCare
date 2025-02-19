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
import com.an1ee.petcare.ui.activity.ProductDetailsActivity
import com.an1ee.petcare.ui.activity.ProfileActivity
import com.an1ee.petcare.ui.activity.ProductDashboardActivity


class ToyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toy)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonProfile = findViewById<ImageView>(R.id.button_profile)
        val buttonHome = findViewById<ImageView>(R.id.button_home)
        val buttonCart = findViewById<ImageView>(R.id.button_cart)

        val productCards = listOf(
            findViewById<CardView>(R.id.card_ball),
            findViewById<CardView>(R.id.card_hiddenFood),
            findViewById<CardView>(R.id.card_corn),
            findViewById<CardView>(R.id.card_zooms),
            findViewById<CardView>(R.id.card_softToy),
            findViewById<CardView>(R.id.card_rubberBone)
        )

        val productData = listOf(
            ProductData("Ball", "$49.99", "High-quality interactive ball toy", R.drawable.toy1),
            ProductData("Hidden Food", "$59.99", "Toy with hidden treat compartment", R.drawable.toy2),
            ProductData("Corn", "$39.99", "Corn-shaped chew toy", R.drawable.toy3),
            ProductData("Zooms", "$39.99", "Fast-moving interactive toy", R.drawable.toy4),
            ProductData("Soft Toy", "$39.99", "Soft and cuddly plush toy", R.drawable.toy5),
            ProductData("Rubber Bone", "$39.99", "Durable rubber chew bone", R.drawable.toy6)
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
            startActivity(Intent(this, ProductCartActivity::class.java))
        }
    }
}