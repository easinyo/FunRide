package com.dubinostech.rideshareapp.model.loginModel

import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse


interface SearchCallBack {


    fun search(
        departure: String,
        arrival: String,
        date: String,
        searchFinishedListener: IOnSearchFinishedListener
    )

    interface IOnSearchFinishedListener {

        fun getTripData(trips: List<SearchResponse>)

        fun errorMsg(errorMsg: String)
    }
}
