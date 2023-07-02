package com.example.boapp.main.customers.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.databinding.DialogRegisterCustomerBinding
import com.example.boapp.framework.extension.showToastFailed
import com.example.boapp.framework.extension.showToastSuccess
import com.example.boapp.framework.interfaces.OnListenerAddCustomer
import com.example.boapp.main.customers.viewmodel.BOViewModelCustomer

class BODialogCreateCustomer(
    private var onListenerAddCustomer: OnListenerAddCustomer? = null
) : DialogFragment() {

    private lateinit var binding: DialogRegisterCustomerBinding
    private lateinit var safeActivity: Activity

    private val viewModelCustomer: BOViewModelCustomer by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRegisterCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserves()
    }

    private fun initListener() {
        binding.btnSaved.setOnClickListener {
            viewModelCustomer.insertCustomer(
                CustomerEntity(
                    name = binding.etName.text.toString(),
                    note = binding.etNota.text.toString()
                )
            )
        }
    }

    private fun initObserves() {
        viewModelCustomer.customerInserted.observe(viewLifecycleOwner, handleInserted())
    }

    private fun handleInserted(): (Boolean) -> Unit = { response ->
        if (response) {
            binding.etName.setText("")
            binding.etNota.setText("")
            Toast(safeActivity).showToastSuccess("El cliente se ha registrado con éxito.", safeActivity)
            onListenerAddCustomer?.onItemAdded(true)
            dismiss()
        } else {
            Toast(safeActivity).showToastFailed("Error al registrar al cliente, intente nuevamente.", safeActivity)
        }
    }

}