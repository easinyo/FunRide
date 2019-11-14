package com.dubinostech.rideshareapp.presenter.interfaces

interface LoginPresenterInterface<V> {

    fun attachedView(view: V)

    fun detachView()
}