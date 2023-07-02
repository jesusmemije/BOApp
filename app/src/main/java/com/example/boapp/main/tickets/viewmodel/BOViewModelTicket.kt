package com.example.boapp.main.tickets.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.boapp.database.entities.CustomerEntity
import com.example.boapp.database.entities.TicketEntity
import com.example.boapp.framework.base.BOConstant
import com.example.boapp.framework.base.BOViewModelBase
import com.example.boapp.framework.commons.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BOViewModelTicket : BOViewModelBase() {

    private var ticketListMLD = SingleLiveEvent<List<TicketEntity>?>()
    val ticketList: LiveData<List<TicketEntity>?>
        get() = ticketListMLD

    private var ticketInsertedMLD = SingleLiveEvent<Boolean>()
    val ticketInserted: LiveData<Boolean>
        get() = ticketInsertedMLD

    private var ticketUpdatedMLD = SingleLiveEvent<Boolean>()
    val ticketUpdated: LiveData<Boolean>
        get() = ticketUpdatedMLD

    fun getTickets(customerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val customers = BOConstant.APP_DATABASE?.ticketDao()?.getTickets(customerId)
            Handler(Looper.getMainLooper()).postDelayed({
                ticketListMLD.postValue(customers)
            }, 500)
        }
    }

    fun insertTicket(ticketEntity: TicketEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val addedID = BOConstant.APP_DATABASE?.ticketDao()?.insertTicket(ticketEntity)
            Handler(Looper.getMainLooper()).postDelayed({
                if (addedID != null && addedID > 0) {
                    ticketInsertedMLD.postValue(true)
                } else {
                    ticketInsertedMLD.postValue(false)
                }
                isLoading.postValue(false)
            }, 500)
        }
    }

    fun editProductQuantity(id: Int, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val updatedID = BOConstant.APP_DATABASE?.ticketDao()?.editProductQuantity(id, quantity)
            if (updatedID != null && updatedID > 0) {
                ticketUpdatedMLD.postValue(true)
            } else {
                ticketUpdatedMLD.postValue(false)
            }
        }
    }
}