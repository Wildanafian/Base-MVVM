package com.base.myapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

class BaseResponse {
    @SerializedName("response_code")
    var responseCode: String? = ""

    @SerializedName("message")
    var message: String? = ""

    @SerializedName("data")
    var data: String? = ""
}