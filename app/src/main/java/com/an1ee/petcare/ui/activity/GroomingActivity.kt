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



class GroomingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_grooming)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonHome = findViewById<ImageView>(R.id.button_home)
        val buttonProfile = findViewById<ImageView>(R.id.button_profile)
        val buttonCart = findViewById<ImageView>(R.id.button_cart)

        val productCards = listOf(
            findViewById<CardView>(R.id.card_hammock_hanger),
            findViewById<CardView>(R.id.card_hair_care),
            findViewById<CardView>(R.id.card_groom_kit),
            findViewById<CardView>(R.id.card_hair_brush),
            findViewById<CardView>(R.id.card_dryer),
            findViewById<CardView>(R.id.card_bath_robe)
        )

        val productData = listOf(
            ProductData("Hammock Hanger", "$49.99", "Comfortable pet grooming hammock", R.drawable.gro1),
            ProductData("Hair Care", "$59.99", "Professional pet hair care kit", R.drawable.gro2),
            ProductData("GroomKit", "$39.99", "Complete grooming accessory set", R.drawable.gro3),
            ProductData("HairBrush", "$39.99", "Gentle pet hair brush", R.drawable.gro4),
            ProductData("Dryer", "$39.99", "Pet hair dryer", R.drawable.gro5),
            ProductData("BathRobe", "$39.99", "Soft pet bath robe", R.drawable.gro6)
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