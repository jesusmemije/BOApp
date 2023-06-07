package com.example.boapp.framework.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BOViewModelBase : ViewModel() {
    val launchLoader = MutableLiveData<Boolean>()
    val launchSuccess = MutableLiveData<String>()
    val launchError = MutableLiveData<String>()

    protected fun showSuccess(message: String) {
        launchSuccess.postValue(message)
    }

    protected fun showError(message: String) {
        launchError.postValue(message)
    }
}