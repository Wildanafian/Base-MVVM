package com.spe.baseapp.features

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.base.myapplication.base.BaseFragment
import com.base.myapplication.databinding.ActivityMainBinding
import com.base.myapplication.features.ExampleAdapter
import com.base.myapplication.features.MainActivityViewModel

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

class ExampleFragment: BaseFragment<ActivityMainBinding>() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val adapter by lazy { ExampleAdapter() }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun initView() {
//        bind.tvHello.setVisile()

//        viewLifecycleOwner.lifecycleScope.launch {
//
//        }

//        displayInfoDialog(message = "Your Message")
    }

    override fun initListener() {
//        bind.tvHello.setOnClickListener {
//            //do something
//            //gooTo(SomeFragmentDirections.actionSomeFragmentToDetailFragment(toJson(dataHeadline)))
//        }
//
//        adapter.someClickListener = {
//            //do something
//        }
    }

    override fun initObserver() {
        viewModel.getSomething().observe(viewLifecycleOwner) {
            //observe something
//            it.makeToast()
//            it.writeToPref("key")
//            FCMID.readFromPref()
//            "key".makeToast()
        }
    }
}