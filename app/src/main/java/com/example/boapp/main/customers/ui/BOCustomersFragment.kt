package com.example.boapp.main.customers.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boapp.R
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.databinding.FragmentBoCustomersBinding
import com.example.boapp.framework.base.BOFragmentBase
import com.example.boapp.framework.extension.showToastFailed
import com.example.boapp.framework.extension.showToastInfo
import com.example.boapp.framework.extension.showToastSuccess
import com.example.boapp.framework.interfaces.ClickListenerPosition
import com.example.boapp.framework.interfaces.OnListenerAddCustomer
import com.example.boapp.main.customers.adapter.CustomerAdapter
import com.example.boapp.main.customers.util.BODialogCreateCustomer
import com.example.boapp.main.customers.viewmodel.BOViewModelCustomer
import com.example.boapp.main.tickets.viewmodel.BOViewModelTicket


class BOCustomersFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoCustomersBinding
    private lateinit var safeActivity: Activity

    private val viewModelCustomer: BOViewModelCustomer by viewModels()
    private val viewModelTicket: BOViewModelTicket by viewModels()

    var customerId: Int = 0

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
        binding.refresh.setOnRefreshListener {
            findNavController().navigate(R.id.action_navigation_customers_self)
        }
        viewModelCustomer.getCustomers()
        binding.toolbar.ivAdd.setOnClickListener {
            val dialog = BODialogCreateCustomer(object : OnListenerAddCustomer {
                override fun onItemAdded(ok: Boolean) {
                    viewModelCustomer.getCustomers()
                }
            })
            dialog.show(childFragmentManager, "DialogCreateCustomer")
        }
    }

    private fun initObserves() {
        viewModelCustomer.customerList.observe(viewLifecycleOwner, handleCustomers())
        viewModelCustomer.customerRemoved.observe(viewLifecycleOwner, handleCustomerRemoved())
        viewModelCustomer.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.loader.contentLoading.isVisible = isVisible
        }
    }

    private fun handleCustomers(): (List<CustomerEntity>?) -> Unit = { customerList ->
        if (!customerList.isNullOrEmpty()) {
            val clickListenerGoDetail = object : ClickListenerPosition {
                override fun onItemClick(position: Int) {
                    val mBundle = Bundle()
                    mBundle.putString("customerId", customerList[position].id.toString())
                    findNavController().navigate(R.id.action_navigation_customers_to_BOTicketsFragment, mBundle)
                }
            }
            val clickListenerDelete = object : ClickListenerPosition {
                override fun onItemClick(position: Int) {
                    val dialog: AlertDialog = AlertDialog.Builder(safeActivity)
                        .setPositiveButton("Sí, eliminar") { _, _ ->
                            customerId = customerList[position].id
                            viewModelCustomer.deleteCustomer(customerList[position])
                        }.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                        .setTitle("Confirmar")
                        .setMessage("¿Realmente deseas eliminar al cliente y toda su información registrada?").create()
                    dialog.show()
                }
            }
            val productAdapter = CustomerAdapter(customerList, clickListenerGoDetail, clickListenerDelete)
            binding.rvCustomers.adapter = productAdapter
            binding.rvCustomers.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        } else {
            binding.rvCustomers.adapter = CustomerAdapter(emptyList())
            Toast(safeActivity).showToastInfo("No se encontraron clientes registrados.", safeActivity)
        }
    }

    private fun handleCustomerRemoved(): (Boolean) -> Unit = { isDeleted ->
        if (isDeleted) {
            viewModelCustomer.getCustomers()
            viewModelTicket.deleteTicketForCustomer(customerId)
            Toast(safeActivity).showToastSuccess("Se ha eliminado el cliente y su información relacionada.", safeActivity)
        } else {
            Toast(safeActivity).showToastFailed("Hemos tenido problemas al eliminar al cliente", safeActivity)
        }
    }

}