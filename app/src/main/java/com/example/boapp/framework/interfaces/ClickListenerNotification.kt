package com.example.boapp.framework.interfaces

import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.database.entities.ProductEntity

interface ClickListenerProduct {
    fun onItemClick(item: ProductEntity)
}

interface ClickListenerCustomer {
    fun onItemClick(item: CustomerEntity)
}