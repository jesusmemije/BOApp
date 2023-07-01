package com.example.boapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boapp.database.entities.CustomerEntity

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customer_table ORDER BY id DESC")
    suspend fun getCustomers(): List<CustomerEntity>

    @Query("SELECT * FROM customer_table WHERE id =:id")
    fun getCustomerById(id: Int): CustomerEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customerEntity: CustomerEntity): Long

    @Delete
    suspend fun deleteCustomer(customerEntity: CustomerEntity)
}