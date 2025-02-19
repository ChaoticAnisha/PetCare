package com.an1ee.petcare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.an1ee.petcare.R
import com.an1ee.petcare.model.CartItem

class CartAdapter(
    private var items: MutableList<CartItem>,
    private val onQuantityChanged: (Int, Int) -> Unit  // Changed from (String, Int) to (Int, Int)
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageProduct: ImageView = view.findViewById(R.id.image_product)
        val textName: TextView = view.findViewById(R.id.text_product_name)
        val textPrice: TextView = view.findViewById(R.id.text_product_price)
        val textQuantity: TextView = view.findViewById(R.id.text_quantity)
        val buttonIncrease: ImageButton = view.findViewById(R.id.button_increase)
        val buttonDecrease: ImageButton = view.findViewById(R.id.button_decrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        holder.imageProduct.setImageResource(item.imageResource)
        holder.textName.text = item.name
        holder.textPrice.text = String.format("$%.2f", item.price)
        holder.textQuantity.text = item.quantity.toString()

        holder.buttonIncrease.setOnClickListener {
            onQuantityChanged(item.id, item.quantity + 1)
        }

        holder.buttonDecrease.setOnClickListener {
            if (item.quantity > 1) {
                onQuantityChanged(item.id, item.quantity - 1)
            } else {
                onQuantityChanged(item.id, 0) // This will remove the item
            }
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<CartItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}