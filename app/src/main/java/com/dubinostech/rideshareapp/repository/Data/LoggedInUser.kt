package com.dubinostech.rideshareapp.repository.Data

import java.io.Serializable
/**
 * Created by Emmanuel Asinyo
 */
 class LoggedInUser: Serializable {

    private var name: String? = null
    private var email: String? = null
    private var objectId: String? = null
    private var token: String? = null

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getObjectId(): String? {
        return objectId
    }

    fun setObjectId(objectId: String) {
        this.objectId = objectId
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String) {
        this.token = token
    }
}


