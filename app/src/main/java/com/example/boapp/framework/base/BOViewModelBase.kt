package com.example.boapp.framework.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BOViewModelBase : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

}