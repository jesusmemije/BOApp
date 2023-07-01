package com.example.boapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket_table")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "customerId") val customerId: Int,
    @ColumnInfo(name = "productId") val productId: Int,
    @ColumnInfo(name = "productName") val productName: String,
    @ColumnInfo(name = "productPrice") val productPrice: Int,
    @ColumnInfo(name = "quantity") val quantity: Int
)