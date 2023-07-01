package com.example.boapp.main.tickets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.database.entities.TicketEntity
import com.example.boapp.databinding.ItemTicketProductBinding
import com.example.boapp.framework.interfaces.ClickListenerPosition

class TicketProductAdapter(
    private val ticketList: List<TicketEntity>,
    private var clickListenerLess: ClickListenerPosition? = null,
    private var clickListenerMore: ClickListenerPosition? = null
) : RecyclerView.Adapter<TicketProductAdapter.TicketProductAdapterHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TicketProductAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTicketProductBinding.inflate(inflater, parent, false)
        return TicketProductAdapterHolder(binding)
    }

    override fun getItemCount(): Int = ticketList.size

    override fun onBindViewHolder(holder: TicketProductAdapterHolder, position: Int) {
        holder.bind(ticketList[position], position)
    }

    inner class TicketProductAdapterHolder(private val binding: ItemTicketProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketEntity, position: Int) {
            binding.apply {
                tvProductName.text = item.productName
                tvQuantity.text = item.quantity.toString()
                ivButtonLess.setOnClickListener {
                    clickListenerLess?.onItemClick(position)
                }
                ivButtonMore.setOnClickListener {
                    clickListenerMore?.onItemClick(position)
                }
            }
        }
    }
}
