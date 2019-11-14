package com.dubinostech.rideshareapp.data


open class BaseResponse {

    private val SUCCESS = 0
    private var code: Int = 0
    private var message: String? = null


    fun isSuccess(): Boolean {
        return this.code == SUCCESS
    }

    fun getCode(): Int {
        return this.code
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getMessage(): String? {
        return this.message
    }

    fun setMessage(message: String) {
        this.message = message
    }
}

