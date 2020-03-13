package com.dubinostech.rideshareapp.model.loginModel

import com.dubinostech.rideshareapp.repository.Api.GatewayAPI
import com.dubinostech.rideshareapp.repository.Api.Raws.SearchRaw
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse
import com.dubinostech.rideshareapp.repository.ErrorHandler.WebErrorUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchModel : SearchCallBack {

    private val TAG = "LoginModel"

    private var gatewayAPI: GatewayAPI? = null

    override fun search(
        departure: String,
        arrival: String,
        date: String,
        searchFinishedListener: SearchCallBack.IOnSearchFinishedListener
    ) {

        gatewayAPI = GatewayAPI(null)
        val searchRaw =
            SearchRaw(departure, arrival, date)

        val responseSearchCallback = gatewayAPI!!.search(searchRaw)

        responseSearchCallback.enqueue(object : Callback<List<SearchResponse>> {
            override fun onResponse(
                call: Call<List<SearchResponse>>,
                response: Response<List<SearchResponse>>
            ) {
                if (response.body() != null && response.isSuccess) {
                    if (response.code() == 202)
                        searchFinishedListener.errorMsg("Something went  wrong !! Please try again later.")
                    else searchFinishedListener.getTripData(response.body())
                } else {

                    if (response.errorBody() != null) {
                        val error = WebErrorUtils.parseError(response)
                        error?.message?.let { searchFinishedListener.errorMsg(it) }
                    } else {
                        searchFinishedListener.errorMsg("Problem getting trip !! Try again later.")
                    }
                }
            }

            override fun onFailure(call: Call<List<SearchResponse>>, t: Throwable) {
                searchFinishedListener.errorMsg("Problem getting rides !! Try again later.")
            }
        })
        }
}