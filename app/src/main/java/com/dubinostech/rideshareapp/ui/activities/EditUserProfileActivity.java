package com.dubinostech.rideshareapp.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.model.userModel.UserModel;
import com.dubinostech.rideshareapp.presenter.UserPresenter;
import com.dubinostech.rideshareapp.repository.Api.Responses.UserInfoResponse;
import com.dubinostech.rideshareapp.repository.Data.LoggedUser;
import com.dubinostech.rideshareapp.repository.Data.User;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.repository.Libraries.Utils;
import com.dubinostech.rideshareapp.ui.view.UserInfoView;


public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener, UserInfoView {
    private final static String TAG = "EditUserProfileActivity";

    private Button savesChanges;
    private ProgressDialog progressDialog;
    private EditText firstname, lastname,email,phone,password,confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        savesChanges = findViewById(R.id.savesChanges);
        firstname = findViewById(R.id.efirstname);
        lastname = findViewById(R.id.elastname);
        email = findViewById(R.id.eemail);
        phone = findViewById(R.id.ephone);
        password = findViewById(R.id.epassword);
        confirmpassword = findViewById(R.id.econfirmpassword);

        firstname.setText(LoggedUser.firstname);
        lastname.setText(LoggedUser.lastname);
        email.setText(LoggedUser.email);
        phone.setText(LoggedUser.phone_number);

        this.progressDialog = new ProgressDialog(this);

        savesChanges.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.savesChanges) {
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
            UserPresenter presenter = new UserPresenter(this, new UserModel());
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
        if (progressDialog != null && progressDialog.isShowing())
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

        Log.d(TAG, " -> " + LoggedUser.email + " - " + LoggedUser.phone_number + " - " + LoggedUser.firstname + " - " + LoggedUser.lastname + " - " + LoggedUser.token);

       progressDialog.setMessage("Your changes are saved successfully");
        progressDialog.show();

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }
        }.start();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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