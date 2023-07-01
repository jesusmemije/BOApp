package com.example.boapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boapp.database.entities.TicketEntity

@Dao
interface TicketDao {

    @Query("SELECT * FROM ticket_table ORDER BY id DESC")
    suspend fun getTickets(): List<TicketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticketEntity: TicketEntity): Long
}