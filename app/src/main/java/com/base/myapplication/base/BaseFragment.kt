package com.base.myapplication.base

import android.app.AlertDialog
import android.content.Context
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
import com.base.myapplication.R
import com.base.myapplication.databinding.CustomLoadingBinding
import com.bumptech.glide.Glide
import com.crowdfire.cfalertdialog.CFAlertDialog

/**
 * Created by Wildan Nafian on 12/01/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

abstract class BaseFragment<out VB : ViewBinding> : Fragment(), BaseCommonFunction {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val bind: VB
        get() = this._binding as VB

    private var _loadingBinding: CustomLoadingBinding? = null
    private val loadingBinding get() = _loadingBinding!!
    private lateinit var loadingAlert: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater)
        initView()
        initListener()
        initObserver()
        return requireNotNull(_binding).root
    }

    override fun onDestroy() {
        super.onDestroy()
        showLoadingDialog(false)
        _binding = null
    }

    override fun initView() {
        setBaseLoading(requireContext())
    }

    override fun initListener() {}

    override fun initObserver() {}

    override fun String?.makeToast() {
        Toast.makeText(requireContext(), this ?: "null", Toast.LENGTH_SHORT).show()
    }

    protected fun gooTo(directions: NavDirections) = findNavController().navigate(directions)

    private fun showDialog(title: String, message: String, button: String) {
        val builder = CFAlertDialog.Builder(requireContext())
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
        builder.addButton(
            button,
            Color.parseColor("#ffffff"),
            Color.parseColor("#429ef4"),
            CFAlertDialog.CFAlertActionStyle.POSITIVE,
            CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    fun makeInfoDialog(title: String = "Info", message: String, button: String = "OK") {
        showDialog(title, message, button)
    }

    private fun setBaseLoading(context: Context) {
        _loadingBinding = CustomLoadingBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(loadingBinding.root)

        Glide.with(context)
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