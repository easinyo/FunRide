package com.dubinostech.rideshareapp.repository.Api.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserInfoResponse {

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("firstName")
    @Expose
    private var firstName: String? = null

    @SerializedName("lastName")
    @Expose
    private var lastName: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("phone")
    @Expose
    private var phone: String? = null

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
     * The firstName
     */
    fun getFirstName(): String? {
        return firstName
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    /**
     *
     * @return
     * The lastName
     */
    fun getLastName(): String? {
        return lastName
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    fun setLastName(lastName: String) {
        this.lastName = lastName
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

    /**
     *
     * @return
     * The phone
     */
    fun getPhone(): String? {
        return phone
    }

    /**
     *
     * @param phone
     * The phone
     */
    fun setPhone(phone: String) {
        this.phone = phone
    }

}
