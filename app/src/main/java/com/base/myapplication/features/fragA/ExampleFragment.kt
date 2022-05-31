package com.base.myapplication.features.fragA

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.base.myapplication.base.BaseFragment
import com.base.myapplication.data.repository.remote.network.ConsumeResult
import com.base.myapplication.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@AndroidEntryPoint
class ExampleFragment: BaseFragment<FragmentMainBinding>() {

    private val viewModel: ExampleFragmentViewModel by viewModels()
    private val adapter by lazy { ExampleAdapter() }

    override val bindingInflater: (LayoutInflater) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun initListener() {
        bind.btnMoveToB.setOnClickListener {
            gooTo(ExampleFragmentDirections.actionExampleFragmentToBFragment())
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