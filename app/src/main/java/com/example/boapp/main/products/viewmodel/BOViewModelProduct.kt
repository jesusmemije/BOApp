package com.example.boapp.main.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.framework.base.BOConstant.APP_DATABASE
import com.example.boapp.framework.base.BOViewModelBase
import com.example.boapp.framework.extension.log
import kotlinx.coroutines.launch

class BOViewModelProduct : BOViewModelBase() {

    val productModel = MutableLiveData<List<ProductEntity>?>()

    fun getProducts() {
        viewModelScope.launch {
            val products = APP_DATABASE?.productDao()?.getProducts()
            productModel.postValue(products)
        }
    }

    fun insertProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            val addedID = APP_DATABASE?.productDao()?.insertProduct(productEntity)
            if (addedID != null && addedID > 0) {
                showSuccess("El producto se ha registrado con éxito.")
            } else {
                showError("Error al registrar producto, intente nuevamente.")
            }
        }
    }

    fun deleteProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            APP_DATABASE?.productDao()?.deleteProduct(productEntity)
            // "ID DELETE $addedID".log()
            /* if (addedID != null && addedID > 0) {
                "ID DELETE $addedID".log()
                showSuccess("El producto se ha eliminado con éxito.")
            } else {
                showError("Error al eliminar producto, intente nuevamente.")
            } */
        }
    }

}