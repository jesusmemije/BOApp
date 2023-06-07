package com.example.boapp.main.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.databinding.ItemProductBinding
import com.example.boapp.framework.interfaces.ClickListenerProduct
import com.example.boapp.framework.extension.toFormatCoinMXN

class ProductAdapter(
    private val productList: List<ProductEntity>,
) : RecyclerView.Adapter<ProductAdapter.ProductAdapterHolder>() {

    private var clickListener: ClickListenerProduct? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductAdapterHolder(binding)
    }

    fun setOnItemClickListener(clickListener: ClickListenerProduct) {
        this.clickListener = clickListener
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductAdapterHolder, position: Int) {
        holder.bind(productList[position], position)
    }

    inner class ProductAdapterHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root), ClickListenerProduct {
        fun bind(item: ProductEntity, position: Int) {
            binding.apply {
                tvName.text = item.name
                tvPrice.text = item.price.toFormatCoinMXN()
                tvDescription.text = item.description.ifEmpty { "Sin descripción" }
            }
        }

        override fun onItemClick(item: ProductEntity) {
            clickListener?.onItemClick(item)
        }
    }
}
