package com.example.boapp.main.products.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.boapp.R
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.databinding.FragmentBoCreateProductBinding
import com.example.boapp.framework.base.BOFragmentBase
import com.example.boapp.framework.extension.showToastFailed
import com.example.boapp.framework.extension.showToastInfo
import com.example.boapp.framework.extension.showToastSuccess
import com.example.boapp.main.products.viewmodel.BOViewModelProduct

class BOCreateProductFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoCreateProductBinding
    private lateinit var safeActivity: Activity

    private val viewModelProduct: BOViewModelProduct by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoCreateProductBinding.inflate(inflater, container, false)
        binding.toolbar.tvTitle.text = resources.getString(R.string.add_product)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserves()
    }

    private fun initListener() {

        binding.toolbar.btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAdd.setOnClickListener {

            val name = binding.etName.text.toString()
            val price = binding.etPrice.text.toString()
            val description = binding.etDescription.text.toString()

            if (name.isNotEmpty() && price.isNotEmpty()) {
                viewModelProduct.insertProduct(
                    ProductEntity(
                        name = name,
                        price = price.toInt(),
                        description = description
                    )
                )
            } else {
                Toast(safeActivity).showToastInfo("Debe llenar los datos que son requeridos.", safeActivity)
            }
        }
    }

    private fun initObserves() {
        viewModelProduct.launchSuccess.observe(viewLifecycleOwner, handleLaunchSuccess())
        viewModelProduct.launchError.observe(viewLifecycleOwner, handleLaunchError())
    }

    private fun handleLaunchSuccess(): (String) -> Unit = { response ->
        binding.etName.setText("")
        binding.etPrice.setText("")
        binding.etDescription.setText("")
        Toast(safeActivity).showToastSuccess(response, safeActivity)
    }

    private fun handleLaunchError(): (String) -> Unit = { response ->
        Toast(safeActivity).showToastFailed(response, safeActivity)
    }
}