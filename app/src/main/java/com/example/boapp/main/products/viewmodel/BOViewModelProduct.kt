package com.example.boapp.main.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.framework.base.BOConstant.APP_DATABASE
import kotlinx.coroutines.launch

class BOViewModelProduct : ViewModel() {

    val productModel = MutableLiveData<List<ProductEntity>?>()

    fun getProducts() {
        viewModelScope.launch {
            val products = APP_DATABASE?.productDao()?.getProducts()
            productModel.postValue(products)
        }
    }
}