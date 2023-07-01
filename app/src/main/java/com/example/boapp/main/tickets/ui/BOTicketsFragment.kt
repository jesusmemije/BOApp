package com.example.boapp.main.tickets.ui

import android.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.database.entities.TicketEntity
import com.example.boapp.databinding.FragmentBoTicketsBinding
import com.example.boapp.framework.base.BOFragmentBase
import com.example.boapp.framework.extension.showToastFailed
import com.example.boapp.framework.extension.showToastInfo
import com.example.boapp.framework.extension.showToastSuccess
import com.example.boapp.main.customers.viewmodel.BOViewModelCustomer
import com.example.boapp.main.products.viewmodel.BOViewModelProduct
import com.example.boapp.main.tickets.viewmodel.BOViewModelTicket

class BOTicketsFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoTicketsBinding
    private lateinit var safeActivity: Activity

    private val viewModelCustomer: BOViewModelCustomer by viewModels()
    private val viewModelProduct: BOViewModelProduct by viewModels()
    private val viewModelTicket: BOViewModelTicket by viewModels()

    private var customerId: String = "0"
    private var customer: CustomerEntity? = null
    private var productList: List<ProductEntity>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        initListener()
        initObserves()
    }

    private fun initArguments() {
        val bundle = this.arguments
        if (bundle != null) {
            customerId = bundle.getString("customerId", "0")
        }
    }

    private fun initListener() {
        if (customerId != "0") {
            binding.toolbar.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }
            viewModelCustomer.getCustomerById(customerId.toInt())
            viewModelProduct.getProducts()
            binding.btnAdd.setOnClickListener {
                if (binding.etQuantity.text?.isNotEmpty() == true) {
                    val selectedProductList = productList?.filter { product -> product.name == binding.spinnerProducts.selectedItem.toString() }
                    viewModelTicket.insertTicket(
                        TicketEntity(
                            customerId = customer?.id ?: 0,
                            productId = selectedProductList?.firstOrNull()?.id ?: 0,
                            productName = selectedProductList?.firstOrNull()?.name ?: "",
                            productPrice = selectedProductList?.firstOrNull()?.price ?: 0,
                            quantity = binding.etQuantity.text.toString().toInt()
                        )
                    )
                } else {
                    Toast(safeActivity).showToastInfo("No ha ingresado la cantidad a agregar", safeActivity)
                }
            }
        } else {
            findNavController().popBackStack()
            Toast(safeActivity).showToastInfo("No se recibió ningún cliente", safeActivity)
        }
    }

    private fun initObserves() {
        viewModelCustomer.customer.observe(viewLifecycleOwner, handleCustomer())
        viewModelProduct.productList.observe(viewLifecycleOwner, handleProductList())
        viewModelTicket.ticketInserted.observe(viewLifecycleOwner, handleTicketInserted())
        viewModelCustomer.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.loader.contentLoading.isVisible = isVisible
        }
    }

    private fun handleCustomer(): (CustomerEntity?) -> Unit = { response ->
        if (response != null) {
            customer = response
            binding.toolbar.tvTitle.text = customer?.name
        } else {
            findNavController().popBackStack()
            Toast(safeActivity).showToastInfo("No encontramos ningún cliente", safeActivity)
        }
    }

    private fun handleProductList(): (List<ProductEntity>?) -> Unit = { response ->
        productList = response
        if (productList?.isNotEmpty() == true) {
            val products = mutableListOf<String>()
            productList?.forEach { product ->
                products.add(product.name)
            }
            binding.spinnerProducts.adapter = ArrayAdapter(
                safeActivity,
                R.layout.simple_spinner_dropdown_item,
                products
            )
        } else {
            findNavController().popBackStack()
            Toast(safeActivity).showToastInfo("Necesita registrar al menos un producto", safeActivity)
        }
    }

    private fun handleTicketInserted(): (Boolean) -> Unit = { response ->
        if (response) {
            Toast(safeActivity).showToastSuccess("Se ha insertado el producto al cliente", safeActivity)
        } else {
            Toast(safeActivity).showToastFailed("No se puedo insertar el producto al cliente", safeActivity)
        }
    }
}