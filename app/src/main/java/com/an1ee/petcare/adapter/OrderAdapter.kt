package com.an1ee.petcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.an1ee.petcare.databinding.ItemOrderBinding
import com.an1ee.petcare.model.Order
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.apply {
                textOrderId.text = "Order #${order.id}"
                textOrderDate.text = "Date: ${order.date}"
                textOrderTotal.text = "Total: ${NumberFormat.getCurrencyInstance(Locale.US).format(order.total)}"
                textOrderStatus.text = "Status: ${order.status}"
                textOrderItems.text = "Items: ${order.items.joinToString(", ")}"
            }
        }
    }

    private class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}