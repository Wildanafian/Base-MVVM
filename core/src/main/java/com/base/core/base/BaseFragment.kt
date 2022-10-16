package com.base.core.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.base.core.R
import com.base.core.base.common.BaseCommonFunction
import com.base.core.databinding.CustomLoadingBinding
import com.base.core.util.FragmentLifecycleAwareCoroutine
import com.base.core.util.Inflate
import com.bumptech.glide.Glide

/**
 * Created by Wildan Nafian on 12/01/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
abstract class BaseFragment<out VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment(),
    BaseCommonFunction {

    private var _binding: VB? = null
    protected val bind get() = _binding!!

    protected val mainScope by lazy { FragmentLifecycleAwareCoroutine() }

    private var _loadingBinding: CustomLoadingBinding? = null
    private val loadingBinding get() = _loadingBinding!!
    private lateinit var loadingAlert: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater)
        initView()
        initListener()
        initObserver()
        return requireNotNull(_binding).root
    }

    override fun onDestroy() {
        super.onDestroy()
        showLoadingDialog(false)
        _loadingBinding = null
        _binding = null
    }

    override fun initView() {
        initBaseLoading()
    }

    override fun initObserver() {}

    protected fun gooTo(directions: NavDirections) = findNavController().navigate(directions)

    override fun String?.makeToast() =
        Toast.makeText(requireContext(), this ?: "", Toast.LENGTH_SHORT).show()

    private fun initBaseLoading() {
        _loadingBinding = CustomLoadingBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(loadingBinding.root)

        Glide.with(requireContext())
            .asGif()
            .load(R.raw.loading_gif)
            .into(loadingBinding.customLoading)

        loadingAlert = dialogBuilder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    protected fun showLoadingDialog(status: Boolean) {
        if (::loadingAlert.isInitialized) {
            if (status) {
                loadingAlert.show()
            } else {
                loadingAlert.dismiss()
            }
        }
    }
}