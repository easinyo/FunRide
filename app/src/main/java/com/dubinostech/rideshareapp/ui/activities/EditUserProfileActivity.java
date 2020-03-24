package com.dubinostech.rideshareapp.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.SignModel.userModel;
import com.dubinostech.rideshareapp.presenter.UserPresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.UserInfoResponse;
import com.dubinostech.rideshareapp.repository.Data.LoggedUser;
import com.dubinostech.rideshareapp.repository.Data.User;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Libraries.Utils;
import com.dubinostech.rideshareapp.ui.view.UserInfoView;


public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener, UserInfoView {
    private Button signup;
    private ProgressDialog progressDialog;
    private EditText firstname, lastname,email,phone,password,confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        signup = findViewById(R.id.signup);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);

        firstname.setText(LoggedUser.firstname);
        lastname.setText(LoggedUser.lastname);
        email.setText(LoggedUser.email);
        phone.setText(LoggedUser.phone_number);


        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup) {

            updateInfo();
        }
    }

    private void updateInfo(){
        String firstnameStr = firstname.getText().toString();
        String lastnameStr = lastname.getText().toString();
        String emailStr = email.getText().toString();
        String phoneStr = phone.getText().toString();
        String passwordStr = password.getText().toString();
        String confirmPasswordStr = confirmpassword.getText().toString();
        this.progressDialog = new ProgressDialog(this);

        if(firstnameStr.isEmpty())
        {
            displayToast("First name  field empty");
        }
        else if( lastnameStr.isEmpty()){
            displayToast("Last name field empty");
        }
        else if( emailStr.isEmpty()){
            displayToast("Email field empty");
        }

        else if( phoneStr.isEmpty()){
            displayToast("Phone field empty");
        }
        else if( !passwordStr.isEmpty() && confirmPasswordStr.isEmpty()){
            displayToast("confirmPasswordStr field empty");
        }
        else  if(passwordStr.isEmpty() && !confirmPasswordStr.isEmpty()){
            displayToast("confirmPasswordStr field empty");
        }
        else if(!confirmPasswordStr.equals(passwordStr)){
            displayToast("passwords don't match ");
        } else if (!Utils.isNetworkAvailable(this)) {
            Utils.displayCommonAlertDialog(this, String.valueOf(R.string.connection_issue_msg));
        }
        else{
            UserPresenter presenter = new UserPresenter(this, new userModel());
            User user = new User(firstnameStr, lastnameStr, emailStr, phoneStr, passwordStr, confirmPasswordStr);
            presenter.callUserSignUpOrUpdate(user , Utils.EDITPROFILE);
        }
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (progressDialog != null)
        progressDialog.setMessage(this.getString(R.string.activity_loading_msg));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setEmailError(ErrorCode code) {
        if (code.getId() == ErrorCode.EMAIL_INVALID.getId()) {
            email.setError(getString(R.string.activity_login_email_invalid));
        }
    }

    @Override
    public void setPasswordError(ErrorCode code) {
        if (code.getId() == ErrorCode.PASSWORD_INVALID.getId()) {
            password.setError(getString(R.string.activity_login_password_err));
        }
    }

    @Override
    public void onSuccess(UserInfoResponse user) {
        LoggedUser.email = email.getText().toString();
        LoggedUser.phone_number = phone.getText().toString();
        LoggedUser.firstname = firstname.getText().toString();
        LoggedUser.lastname = lastname.getText().toString();

        Utils.displayAlertDialogWithCounter(this, "Your changes are saved successfully");
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(ErrorCode code) {
        if (code.getId() == 5) {
            Toast.makeText(
                    this,
                    String.valueOf(R.string.activity_signup_email_existing),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void onFailure(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show();
    }
}