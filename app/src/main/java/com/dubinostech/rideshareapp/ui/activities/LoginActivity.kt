/**
 * The class LoginAcitivity sets the logic for acitivity_login.xml 
 * Communicate with theLloginPresenter to handle the user login
 */
package com.dubinostech.rideshareapp.ui.activities


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.dubinostech.rideshareapp.R
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode
import com.dubinostech.rideshareapp.repository.Api.Responses.LoginResponse
import com.dubinostech.rideshareapp.repository.Libraries.Utils
import com.dubinostech.rideshareapp.model.loginModel.LoginModel
import com.dubinostech.rideshareapp.presenter.LoginPresenter
import com.dubinostech.rideshareapp.repository.Data.LoggedUser
import com.dubinostech.rideshareapp.ui.view.LoginView

@Suppress("DEPRECATION")
class LoginActivity: BaseActivity() , LoginView {

    private val TAG = "LoginActivity"


    private var username: EditText? = null
    private var password: EditText? = null
    private var login: Button? = null
    private var register: Button? = null

    private var progressDialog: ProgressDialog? = null

    private var presenter : LoginPresenter? = null

    private var usernameString: String? = null
    private var passwordString: String? = null


    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        register = findViewById(R.id.register)

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, 0)

        progressDialog = ProgressDialog(this@LoginActivity)

        presenter = LoginPresenter(this, LoginModel())

        login?.setOnClickListener {
            if (isNotEmpty()) {
                if (Utils.isNetworkAvailable(this@LoginActivity)) {
                    presenter?.callLogin(
                        username?.text.toString(),
                        password?.text.toString()
                    )
                } else {
                    Utils.displayCommonAlertDialog(
                        this@LoginActivity,
                        this@LoginActivity.resources.getString(R.string.connection_issue_msg)
                    )
                }
            }
            else{
                Toast.makeText(this, R.string.username_password_empty, Toast.LENGTH_LONG).show()
            }
        }

        register?.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun isNotEmpty(): Boolean {
        this.usernameString = username?.text.toString().trim { it <= ' ' }
        this.passwordString = password?.text.toString().trim { it <= ' ' }

        return !(TextUtils.isEmpty(passwordString) || TextUtils.isEmpty(usernameString))
    }
    
    /**
     * register method to communicate the username and password to the presenter
     */
    private fun register(username: String, password: String) {
        val bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("password", password)

        //To be changed to registerActivity
        val intent = Intent(this, SignUpActivity::class.java).apply {
            //putExtras(bundle)
        }

        this.startActivity(intent)
    }

    override fun setEmailError(code: ErrorCode?) {
        if (username != null) {
            if (code?.id === ErrorCode.ENTER_EMAIL.id) {
                username?.error = resources.getString(R.string.activity_login_enter_email)
            } else if (code?.id === ErrorCode.EMAIL_INVALID.id) {
                username?.error = resources.getString(R.string.activity_login_email_invalid)
            }
        }    }

    override fun setPasswordError(code: ErrorCode?) {
        if (password != null) {
            if (code?.id === ErrorCode.ENTER_PASSWORD.id) {
                password?.error = resources.getString(R.string.activity_login_enter_password)
            } else if (code?.id === ErrorCode.PASSWORD_INVALID.id) {
                password?.error = resources.getString(R.string.activity_login_password_err)
            }
        }    }

    override fun loginSuccess(user: LoginResponse?) {
        LoggedUser.token = user?.auth_token
        LoggedUser.firstname = user?.firstname
        LoggedUser.lastname = user?.lastname
        LoggedUser.email = user?.email
        LoggedUser.phone_number = user?.phone

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()    }

    override fun loginFailure(code: ErrorCode) {
        if (code.id === 4) {
            Toast.makeText(
                this,
                resources.getString(R.string.activity_login_fail_msg) ,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun loginFailure(code: String) {
        Toast.makeText(this, code, Toast.LENGTH_LONG).show()
    }
    override fun showLoading() {
        progressDialog?.setTitle(null)
        progressDialog?.setTitle(null)
        progressDialog?.setMessage(resources.getString(R.string.activity_loading_msg))
        progressDialog?.show()
    }

    override fun hideLoading() {
        if (progressDialog != null && progressDialog?.isShowing!!)
        progressDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progressDialog != null && progressDialog?.isShowing!!) {
            progressDialog?.cancel()
        }
    }
}



