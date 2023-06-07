package com.example.boapp.main.products.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.R
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.databinding.FragmentBoProductsBinding
import com.example.boapp.framework.base.BOFragmentBase
import com.example.boapp.framework.interfaces.ClickListenerProduct
import com.example.boapp.framework.extension.log
import com.example.boapp.framework.extension.showToastInfo
import com.example.boapp.main.products.adapter.ProductAdapter
import com.example.boapp.main.products.viewmodel.BOViewModelProduct

class BOProductsFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoProductsBinding
    private lateinit var safeActivity: Activity

    private val viewModelProduct: BOViewModelProduct by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoProductsBinding.inflate(inflater, container, false)
        binding.toolbar.tvTitle.text = resources.getString(R.string.my_products)
        binding.toolbar.btnReturn.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserves()
    }

    private fun initListener() {
        viewModelProduct.getProducts()
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_products_to_BOCreateProductFragment)
        }
    }

    private fun initObserves() {
        viewModelProduct.productModel.observe(viewLifecycleOwner, handleProducts())
    }

    private fun handleProducts(): (List<ProductEntity>?) -> Unit = { productList ->
        if (!productList.isNullOrEmpty()) {
            val productAdapter = ProductAdapter(productList)
            binding.rvProducts.adapter = productAdapter
            binding.rvProducts.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            productAdapter.setOnItemClickListener(object : ClickListenerProduct {
                override fun onItemClick(item: ProductEntity) {
                    viewModelProduct.deleteProduct(item)
                    viewModelProduct.getProducts()
                }
            })
        } else {
            Toast(safeActivity).showToastInfo("No se encontraron productos registrados", safeActivity)
        }
    }

}