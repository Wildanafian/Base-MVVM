package com.base.myapplication.features.fragA

import androidx.fragment.app.viewModels
import com.base.core.base.BaseFragment
import com.base.core.datasource.network.ConsumeResult
import com.base.myapplication.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@AndroidEntryPoint
class ExampleFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: ExampleFragmentViewModel by viewModels()
    private val adapter by lazy { ExampleAdapter(this) }

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
                    it.errorMessage.makeToast()
                }
                is ConsumeResult.onLoading -> {
                    showLoadingDialog(it.loading)
                }
            }
        }
    }
}