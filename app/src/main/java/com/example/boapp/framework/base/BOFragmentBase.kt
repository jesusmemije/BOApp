package com.example.boapp.framework.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

open class BOFragmentBase : Fragment() {

    private lateinit var safeActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }
}