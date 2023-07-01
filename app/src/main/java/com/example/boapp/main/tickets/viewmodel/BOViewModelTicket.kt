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

    fun getTickets() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val customers = BOConstant.APP_DATABASE?.ticketDao()?.getTickets()
            Handler(Looper.getMainLooper()).postDelayed({
                ticketListMLD.postValue(customers)
                isLoading.postValue(false)
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
}