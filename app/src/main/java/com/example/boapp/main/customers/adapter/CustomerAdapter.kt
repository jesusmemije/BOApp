package com.example.boapp.main.customers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.databinding.ItemCustomerBinding

class CustomerAdapter(
    private val customerList: List<CustomerEntity>,
) : RecyclerView.Adapter<CustomerAdapter.CustomerAdapterHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CustomerAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomerBinding.inflate(inflater, parent, false)
        return CustomerAdapterHolder(binding)
    }

    override fun getItemCount(): Int = customerList.size

    override fun onBindViewHolder(holder: CustomerAdapterHolder, position: Int) {
        holder.bind(customerList[position])
    }

    inner class CustomerAdapterHolder(private val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CustomerEntity) {
            binding.apply {
                tvLetter.text = item.name[0].toString().uppercase()
                tvName.text = item.name
                tvNote.text = item.note.ifEmpty { "Sin notas" }
            }
        }
    }
}
