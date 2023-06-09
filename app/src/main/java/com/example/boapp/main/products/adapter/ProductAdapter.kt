package com.example.boapp.main.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.databinding.ItemProductBinding
import com.example.boapp.framework.interfaces.ClickListenerProduct
import com.example.boapp.framework.extension.toFormatCoinMXN
import java.util.Locale

class ProductAdapter(
    private val productList: List<ProductEntity>,
) : RecyclerView.Adapter<ProductAdapter.ProductAdapterHolder>() {

    private var clickListenerDelete: ClickListenerProduct? = null
    private var clickListenerEdit: ClickListenerProduct? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductAdapterHolder(binding)
    }

    fun setOnItemClickListenerDelete(clickListener: ClickListenerProduct) {
        this.clickListenerDelete = clickListener
    }

    fun setOnItemClickListenerEdit(clickListener: ClickListenerProduct) {
        this.clickListenerEdit = clickListener
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductAdapterHolder, position: Int) {
        holder.bind(productList[position])
    }

    inner class ProductAdapterHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductEntity) {
            binding.apply {
                tvLetter.text = item.name[0].toString().uppercase()
                tvName.text = item.name
                tvPrice.text = item.price.toFormatCoinMXN()
                tvDescription.text = item.description.ifEmpty { "Sin descripción" }
                ivDelete.setOnClickListener { clickListenerDelete?.onItemClick(item) }
                ivEdit.setOnClickListener { clickListenerEdit?.onItemClick(item) }
            }
        }
    }
}
