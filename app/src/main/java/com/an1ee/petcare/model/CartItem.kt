package com.an1ee.petcare.model

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageResource: Int,
    var quantity: Int = 1
) {
    fun getTotalPrice(): Double = price * quantity
}