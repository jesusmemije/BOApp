package com.example.boapp.main.customers.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.R
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.databinding.FragmentBoCustomersBinding
import com.example.boapp.framework.base.BOFragmentBase
import com.example.boapp.framework.extension.showToastInfo
import com.example.boapp.main.customers.adapter.CustomerAdapter
import com.example.boapp.main.customers.util.BODialogCreateCustomer
import com.example.boapp.main.customers.viewmodel.BOViewModelCustomer

class BOCustomersFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoCustomersBinding
    private lateinit var safeActivity: Activity

    private val viewModelCustomer: BOViewModelCustomer by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoCustomersBinding.inflate(inflater, container, false)
        binding.toolbar.tvTitle.text = resources.getString(R.string.my_customers)
        binding.toolbar.btnReturn.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserves()
    }

    private fun initListener() {
        viewModelCustomer.getCustomers()
        binding.fabAdd.setOnClickListener {
            val dialog = BODialogCreateCustomer()
            dialog.show(childFragmentManager, "DialogCreateCustomer")
        }
    }

    private fun initObserves() {
        viewModelCustomer.customerList.observe(viewLifecycleOwner, handleCustomers())
        viewModelCustomer.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.loader.contentLoading.isVisible = isVisible
        }
    }

    private fun handleCustomers(): (List<CustomerEntity>?) -> Unit = { customerList ->
        if (!customerList.isNullOrEmpty()) {
            val productAdapter = CustomerAdapter(customerList)
            binding.rvCustomers.adapter = productAdapter
            binding.rvCustomers.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        } else {
            binding.rvCustomers.adapter = CustomerAdapter(emptyList())
            Toast(safeActivity).showToastInfo("No se encontraron productos registrados.", safeActivity)
        }
    }
}