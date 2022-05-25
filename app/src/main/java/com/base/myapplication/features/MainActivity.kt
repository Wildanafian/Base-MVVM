package com.base.myapplication.features

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.base.myapplication.base.BaseActivity
import com.base.myapplication.data.repository.remote.network.ConsumeResult
import com.base.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val adapter by lazy { ExampleAdapter() }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun initListener() {
        bind.tvHello.setOnClickListener {
            bind.tvHello.text = viewModel.getPref()
        }

        adapter.someClickListener = {
            //Do something here
        }
    }

    override fun initObserver() {
        viewModel.getSomething().observe(this) {
            when (it) {
                is ConsumeResult.onSuccess -> {
                    bind.tvHello.text = it.data[0].title
                }
                is ConsumeResult.onError -> {
                    it.e.localizedMessage.makeToast()
                }
                is ConsumeResult.onLoading -> {
                    showLoadingDialog(it.loading)
                }
            }
        }
    }

}