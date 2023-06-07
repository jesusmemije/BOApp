package com.example.boapp.main.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boapp.R
import com.example.boapp.databinding.FragmentBoHomeBinding
import com.example.boapp.framework.base.BOFragmentBase

class BOHomeFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoHomeBinding
    private lateinit var safeActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoHomeBinding.inflate(inflater, container, false)
        binding.toolbar.tvTitle.text = resources.getString(R.string.my_home)
        binding.toolbar.btnReturn.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {}
}