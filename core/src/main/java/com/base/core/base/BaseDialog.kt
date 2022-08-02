package com.base.core.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.base.core.base.common.CommonInitialization
import com.base.core.base.common.CommonToast

/**
 * Created by Wildan Nafian on 12/01/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

abstract class BaseDialog(open val activity: Activity, val layout: Int) : Dialog(activity),
    CommonInitialization, CommonToast {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setContentView(layout)

        initView()
        initListener()
    }

    override fun String?.makeToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}