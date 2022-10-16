package com.base.core.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.base.core.R
import com.base.core.base.common.BaseCommonFunction
import com.base.core.databinding.CustomLoadingBinding
import com.base.core.util.Inflate
import com.base.core.util.networkCheck
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Wildan Nafian on 12/01/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

abstract class BaseActivity<out VB : ViewBinding>(private val inflate: Inflate<VB>) :
    AppCompatActivity(), BaseCommonFunction {

    private var _binding: VB? = null
    protected val bind get() = _binding!!

    private lateinit var loadingBinding: CustomLoadingBinding
    private lateinit var loadingAlert: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initView()
        initListener()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        if (!networkCheck())
            Snackbar.make(
                requireNotNull(_binding).root,
                getString(R.string.no_internet),
                Snackbar.LENGTH_SHORT
            ).apply {
                setTextColor(ContextCompat.getColor(this@BaseActivity, R.color.white))
                view.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.red))
            }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        showLoadingDialog(false)
        _binding = null
    }

    override fun initView() {
        initLoading()
    }

    override fun initListener() {}

    override fun initObserver() {}

    override fun String?.makeToast() {
        Toast.makeText(this@BaseActivity, this ?: "null", Toast.LENGTH_SHORT).show()
    }

    private fun initLoading() {
        loadingBinding = CustomLoadingBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(loadingBinding.root)

        Glide.with(this)
            .asGif()
            .load(R.raw.loading_gif)
            .into(loadingBinding.customLoading)

        loadingAlert = dialogBuilder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    fun showLoadingDialog(status: Boolean) {
        if (::loadingAlert.isInitialized) {
            if (status) {
                loadingAlert.show()
            } else {
                loadingAlert.dismiss()
            }
        }
    }
}