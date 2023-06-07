package com.example.boapp.main.products.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.boapp.R
import com.example.boapp.databinding.FragmentBoCreateProductBinding
import com.example.boapp.framework.base.BOFragmentBase

class BOCreateProductFragment : BOFragmentBase() {

    private lateinit var binding: FragmentBoCreateProductBinding
    private lateinit var safeActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoCreateProductBinding.inflate(inflater, container, false)
        binding.toolbar.tvTitle.text = resources.getString(R.string.add_product)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserves()
    }

    private fun initListener() {
        binding.toolbar.btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserves() {

    }
}