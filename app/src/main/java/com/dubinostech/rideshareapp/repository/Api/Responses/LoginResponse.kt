package com.dubinostech.rideshareapp.repository.Api.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse{

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("nickname")
    @Expose
    private var nickname: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    /**
     *
     * @return
     * The id
     */
    fun getId(): String? {
        return id
    }

    /**
     *
     * @param id
     * The id
     */
    fun setId(id: String) {
        this.id = id
    }

    /**
     *
     * @return
     * The nickname
     */
    fun getNickname(): String? {
        return nickname
    }

    /**
     *
     * @param nickname
     * The nickname
     */
    fun setNickname(nickname: String) {
        this.nickname = nickname
    }

    /**
     *
     * @return
     * The email
     */
    fun getEmail(): String? {
        return email
    }

    /**
     *
     * @param email
     * The email
     */
    fun setEmail(email: String) {
        this.email = email
    }

}
