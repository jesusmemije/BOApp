package com.example.boapp.main.products.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.databinding.FragmentBoProductsBinding
import com.example.boapp.framework.base.BOFragmentBase
import com.example.boapp.framework.extension.showToastInfo
import com.example.boapp.framework.extension.showToastSuccess
import com.example.boapp.framework.interfaces.ClickListenerProduct
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
        binding.refresh.setOnRefreshListener {
            findNavController().navigate(R.id.action_navigation_products_self)
        }
        viewModelProduct.getProducts()
        binding.toolbar.ivAdd.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_products_to_BOCreateProductFragment)
        }
    }

    private fun initObserves() {
        viewModelProduct.productList.observe(viewLifecycleOwner, handleProducts())
        viewModelProduct.productDeleted.observe(viewLifecycleOwner, handleDeleted())
        viewModelProduct.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.loader.contentLoading.isVisible = isVisible
        }
    }

    private fun handleProducts(): (List<ProductEntity>?) -> Unit = { productList ->
        if (!productList.isNullOrEmpty()) {
            val productAdapter = ProductAdapter(productList)
            binding.rvProducts.adapter = productAdapter
            binding.rvProducts.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            productAdapter.setOnItemClickListenerDelete(object : ClickListenerProduct {
                override fun onItemClick(item: ProductEntity) {
                    val dialog: AlertDialog = AlertDialog.Builder(safeActivity)
                        .setPositiveButton("Sí, eliminar") { _, _ ->
                            viewModelProduct.deleteProduct(item)
                        }.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                        .setTitle("Confirmar")
                        .setMessage("¿Realmente deseas eliminar este producto?").create()
                    dialog.show()
                }
            })
            productAdapter.setOnItemClickListenerEdit(object : ClickListenerProduct {
                override fun onItemClick(item: ProductEntity) {
                    Toast(safeActivity).showToastInfo("Función no habilitada para editar el producto ${item.name}", safeActivity)
                }
            })
        } else {
            binding.rvProducts.adapter = ProductAdapter(emptyList())
            Toast(safeActivity).showToastInfo("No se encontraron productos registrados.", safeActivity)
        }
    }

    private fun handleDeleted(): (Boolean) -> Unit = { response ->
        if (response) {
            Toast(safeActivity).showToastSuccess("El producto se ha eliminado con éxito.", safeActivity)
            viewModelProduct.getProducts()
        } else {
            Toast(safeActivity).showToastSuccess("Hubo un error al eliminar el producto.", safeActivity)
        }
    }

}