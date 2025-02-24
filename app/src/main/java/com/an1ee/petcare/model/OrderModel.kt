package com.an1ee.petcare.model

data class Order(
    val id: String,
    val date: String,
    val total: Double,
    val status: String,
    val items: List<String>
)