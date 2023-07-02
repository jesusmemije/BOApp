package com.example.boapp.main.customers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.databinding.ItemCustomerBinding
import com.example.boapp.framework.interfaces.ClickListenerPosition

class CustomerAdapter(
    private val customerList: List<CustomerEntity>,
    private var clickListenerGoDetail: ClickListenerPosition? = null,
    private var clickListenerDelete: ClickListenerPosition? = null
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
        holder.bind(customerList[position], position)
    }

    inner class CustomerAdapterHolder(private val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CustomerEntity, position: Int) {
            binding.apply {
                tvLetter.text = item.name[0].toString().uppercase()
                tvName.text = item.name
                tvNote.text = item.note.ifEmpty { "Sin notas" }
                llCustomerItem.setOnClickListener {
                    clickListenerGoDetail?.onItemClick(position)
                }
                ivDelete.setOnClickListener {
                    clickListenerDelete?.onItemClick(position)
                }
            }
        }
    }
}
