package com.example.boapp.main.customers.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.framework.base.BOConstant
import com.example.boapp.framework.base.BOViewModelBase
import com.example.boapp.framework.commons.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BOViewModelCustomer : BOViewModelBase() {

    private var customerListMLD = SingleLiveEvent<List<CustomerEntity>?>()
    val customerList: LiveData<List<CustomerEntity>?>
        get() = customerListMLD

    private var customerMLD = SingleLiveEvent<CustomerEntity?>()
    val customer: LiveData<CustomerEntity?>
        get() = customerMLD

    private var customerInsertedMLD = SingleLiveEvent<Boolean>()
    val customerInserted: LiveData<Boolean>
        get() = customerInsertedMLD

    fun getCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val customers = BOConstant.APP_DATABASE?.customerDao()?.getCustomers()
            Handler(Looper.getMainLooper()).postDelayed({
                customerListMLD.postValue(customers)
                isLoading.postValue(false)
            }, 500)
        }
    }

    fun getCustomerById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val customer = BOConstant.APP_DATABASE?.customerDao()?.getCustomerById(id)
            Handler(Looper.getMainLooper()).postDelayed({
                customerMLD.postValue(customer)
                isLoading.postValue(false)
            }, 500)
        }
    }

    fun insertCustomer(customerEntity: CustomerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val addedID = BOConstant.APP_DATABASE?.customerDao()?.insertCustomer(customerEntity)
            Handler(Looper.getMainLooper()).postDelayed({
                if (addedID != null && addedID > 0) {
                    customerInsertedMLD.postValue(true)
                } else {
                    customerInsertedMLD.postValue(false)
                }
                isLoading.postValue(false)
            }, 500)
        }
    }
}