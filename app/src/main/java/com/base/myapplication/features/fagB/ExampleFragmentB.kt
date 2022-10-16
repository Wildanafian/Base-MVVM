package com.base.myapplication.features.fagB

import androidx.navigation.fragment.navArgs
import com.base.core.base.BaseFragment
import com.base.myapplication.databinding.FragmentBBinding
import com.base.myapplication.features.fragA.ExampleFragmentArgs

class ExampleFragmentB : BaseFragment<FragmentBBinding>(FragmentBBinding::inflate) {

    private val args: ExampleFragmentArgs by navArgs()

    override fun initView() {
        args.data.makeToast()
    }

    override fun initListener() {
        bind.btnMoveToA.setOnClickListener {
            gooTo(ExampleFragmentBDirections.actionBFragmentToExampleFragment())
        }
    }
}