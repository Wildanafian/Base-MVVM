package com.base.core.datasource.model

import com.google.gson.annotations.SerializedName

class BaseResponse {
    @SerializedName("response_code")
    var responseCode: String? = ""

    @SerializedName("message")
    var message: String? = ""

    @SerializedName("data")
    var data: String? = ""
}