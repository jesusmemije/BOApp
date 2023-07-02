package com.example.boapp.main.products.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.boapp.database.entities.ProductEntity
import com.example.boapp.framework.base.BOConstant.APP_DATABASE
import com.example.boapp.framework.base.BOViewModelBase
import com.example.boapp.framework.commons.SingleLiveEvent
import kotlinx.coroutines.launch

class BOViewModelProduct : BOViewModelBase() {

    private var productListMLD = SingleLiveEvent<List<ProductEntity>?>()
    val productList: LiveData<List<ProductEntity>?>
        get() = productListMLD

    private var productInsertedMLD = SingleLiveEvent<Boolean>()
    val productInserted: LiveData<Boolean>
        get() = productInsertedMLD

    private var productDeletedMLD = SingleLiveEvent<Boolean>()
    val productDeleted: LiveData<Boolean>
        get() = productDeletedMLD

    fun getProducts() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val products = APP_DATABASE?.productDao()?.getProducts()
            Handler(Looper.getMainLooper()).postDelayed({
                productListMLD.postValue(products)
                isLoading.postValue(false)
            }, 500)
        }
    }

    fun insertProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val addedID = APP_DATABASE?.productDao()?.insertProduct(productEntity)
            Handler(Looper.getMainLooper()).postDelayed({
                if (addedID != null && addedID > 0) {
                    productInsertedMLD.postValue(true)
                } else {
                    productInsertedMLD.postValue(false)
                }
                isLoading.postValue(false)
            }, 500)
        }
    }

    fun deleteProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            isLoading.postValue(true)
            APP_DATABASE?.productDao()?.deleteProduct(productEntity)
            Handler(Looper.getMainLooper()).postDelayed({
                productDeletedMLD.postValue(true)
                isLoading.postValue(false)
            }, 500)
        }
    }

}