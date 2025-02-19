package com.an1ee.petcare.utils

import android.os.Handler
import android.os.Looper
import com.an1ee.petcare.model.CartItem

object CartManager {
    private val cartItems = mutableListOf<CartItem>()
    private val listeners = mutableSetOf<() -> Unit>()
    private val handler = Handler(Looper.getMainLooper())

    fun addItem(item: CartItem) {
        val existingItem = cartItems.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(item)
        }
        notifyListeners()
    }

    fun removeItem(itemId: Int) {
        cartItems.removeAll { it.id == itemId }
        notifyListeners()
    }

    fun updateQuantity(itemId: Int, quantity: Int) {
        cartItems.find { it.id == itemId }?.let {
            it.quantity = quantity
            if (quantity <= 0) {
                removeItem(itemId)
            }
        }
        notifyListeners()
    }

    fun getItems(): List<CartItem> = cartItems.toList()

    fun getTotal(): Double = cartItems.sumOf { it.price * it.quantity }

    fun clear() {
        cartItems.clear()
        notifyListeners()
    }

    fun addListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: () -> Unit) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        handler.post {
            listeners.toList().forEach { it.invoke() }
        }
    }
}
