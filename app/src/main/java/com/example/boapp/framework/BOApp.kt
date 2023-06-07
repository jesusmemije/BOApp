package com.example.boapp.framework

import android.app.Application
import com.example.boapp.database.BOAppDatabase
import com.example.boapp.framework.base.BOConstant.APP_CONTEXT
import com.example.boapp.framework.base.BOConstant.APP_DATABASE

class BOApp : Application() {
    override fun onCreate() {
        super.onCreate()
        APP_CONTEXT = this
        APP_DATABASE = BOAppDatabase.getInstance(this)
    }
}