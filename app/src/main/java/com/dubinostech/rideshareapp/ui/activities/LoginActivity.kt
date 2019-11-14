package com.dubinostech.rideshareapp.ui.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import butterknife.Bind
import butterknife.ButterKnife
import com.dubinostech.rideshareapp.R
import com.dubinostech.rideshareapp.data.LoggedInUser
import com.dubinostech.rideshareapp.presenter.LoginPresenter
import com.dubinostech.rideshareapp.ui.Base_And_Main_Activities.BaseActivity
import com.dubinostech.rideshareapp.ui.Base_And_Main_Activities.MainActivity
import com.dubinostech.rideshareapp.ui.view.LoginView


class LoginActivity: BaseActivity() , LoginView {
    private val TAG = "LoginActivity"

    @Bind(R.id.login)
    internal var login: View? = null

    @Bind(R.id.register)
    internal var register: View? = null

    @Bind(R.id.llayLoading)
    internal var llayLoading: View? = null

    @Bind(R.id.password)
    internal var password: EditText? = null

    @Bind(R.id.username)
    internal var username: EditText? = null

    @Bind(R.id.loading)
    internal var loading: View? = null

    @Bind(R.id.container)
    internal var container: View? = null

    private lateinit var presenter: LoginPresenter
    private var usernameString: String? = null
    private var passwordString: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        injectView()


        presenter = LoginPresenter()
        presenter.attachedView(this)


        login?.setOnClickListener {
            loading?.visibility = View.VISIBLE
            if (validateForm()) {
                presenter.login(
                    usernameString!!,
                    passwordString!!
                )
            }
        }

        register?.setOnClickListener {
            loading?.visibility = View.VISIBLE
            register(
                usernameString!!,
                passwordString!!
            )
        }
    }


    private fun injectView() {
        ButterKnife.bind(this)
    }

    private fun validateForm(): Boolean {
        this.usernameString = username?.text.toString().trim { it <= ' ' }
        this.passwordString = password?.text.toString().trim { it <= ' ' }

        if (TextUtils.isEmpty(usernameString)) return false
        return !(TextUtils.isEmpty(passwordString)) && (passwordString!!.length > 5)

    }
    private fun register(username: String, password: String) {
        val bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("password", password)

        //To be changed to registerActivity
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtras(bundle)
        }

        this.startActivity(intent)
    }

    override fun showLoading() {
        this.llayLoading?.setVisibility(View.VISIBLE)
    }

    override fun hideLoading() {
        this.llayLoading?.setVisibility(View.GONE)
    }

    override fun showConnectionErrorMessage() {

    }

    override fun showError(message: String) {
        Log.v(TAG, " message $message")

        showMessage(container, message)
    }

    override fun onLoginSuccess(loggedInUser: LoggedInUser) {
        Log.v(TAG, "onLoginsuccess " + loggedInUser.toString())
        val bundle = Bundle()
        bundle.putSerializable("ENTITY", loggedInUser)
        next(bundle, MainActivity::class.java, true)
    }

    fun getContext(): Context {
        return this
    }
}



