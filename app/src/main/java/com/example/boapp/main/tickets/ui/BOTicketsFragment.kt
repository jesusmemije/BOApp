package com.example.boapp.main.tickets.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.databinding.FragmentBoTicketsBinding
import com.example.boapp.framework.base.BOFragmentBase
import com.example.boapp.main.customers.viewmodel.BOViewModelCustomer

class BOTicketsFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoTicketsBinding
    private lateinit var safeActivity: Activity

    private val viewModelCustomer: BOViewModelCustomer by viewModels()

    private var customerId: String = ""

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
        Toast.makeText(safeActivity, "DATA: $customerId", Toast.LENGTH_SHORT).show()
    }

    private fun initListener() {

        binding.toolbar.btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }


        if (customerId != "0") {
            viewModelCustomer.getCustomerById(customerId.toInt())
        }
    }

    private fun initObserves() {
        viewModelCustomer.customer.observe(viewLifecycleOwner, handleCustomer())
        viewModelCustomer.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.loader.contentLoading.isVisible = isVisible
        }
    }

    private fun handleCustomer(): (CustomerEntity?) -> Unit = { customer ->
        println(customer)
        binding.toolbar.tvTitle.text = customer?.name
    }
}