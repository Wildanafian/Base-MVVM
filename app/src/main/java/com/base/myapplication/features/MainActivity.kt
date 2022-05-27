package com.base.myapplication.features

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.base.myapplication.MainApplication
import com.base.myapplication.base.BaseActivity
import com.base.myapplication.data.repository.local.sharedpreference.SharedPref
import com.base.myapplication.data.repository.remote.network.ConsumeResult
import com.base.myapplication.databinding.ActivityMainBinding
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainActivityViewModel by viewModels { viewModelFactory }
    private val adapter by lazy { ExampleAdapter() }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).appComponet.inject(this)
        super.onCreate(savedInstanceState)
    }

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