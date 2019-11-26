package com.dubinostech.rideshareapp

import com.dubinostech.rideshareapp.data.*
import org.hamcrest.CoreMatchers.any
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call as Call

class GatewayApiTest{
    @Mock
    private lateinit var gatewayAPI: GatewayAPI

    @Before
    fun setUp() {
        initMocks(this)
        gatewayAPI = GatewayAPI(null)

        val userName = ""
        val passWord = ""
        val loginRaw = LoginRaw(userName, passWord)



        @Test
        fun testApi(){
            val mockedApiInterface = Mockito()
            //val mockedCall = Mockito.mock(Call<LoginResponse>)
            val responseLoginCallback = gatewayAPI!!.login(loginRaw)


            Mockito.`when`(gatewayAPI.login(loginRaw)).thenReturn(responseLoginCallback)
        //make your tests

    }











    @Test
    fun testLoginWithNonExistedUser() {

    }

    @Test
    fun testLoginWithExistedUser() {

    }




    @Test
    fun testApiResponse() {
        val mockedApiInterface = Mockito()
        val mockedCall = Mockito.mock(Call<LoginResponse>)

        Mockito.`when`(mockedApiInterface.getNotifications()).thenReturn(mockedCall)

        Mockito.doAnswer(object : Answer() {
            @Throws(Throwable::class)
            fun answer(invocation: InvocationOnMock): Void? {
                val callback = invocation.getArgumentAt(0, Call<SignupResponse>)

                callback.onResponse(
                    mockedCall,
                    Response.success<UserNotifications>(UserNotifications())
                )
                // or callback.onResponse(mockedCall, Response.error(404. ...);
                // or callback.onFailure(mockedCall, new IOException());

                return null
            }
        }).`when`(mockedCall).enqueue(any(Callback<*>::class.java))

        // inject mocked ApiInterface to your presenter
        // and then mock view and verify calls (and eventually use ArgumentCaptor to access call parameters)
    }


}