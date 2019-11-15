package com.dubinostech.rideshareapp.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.presenter.SignupPresenter;
import com.dubinostech.rideshareapp.ui.activities.HomeFragment;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private Button signup;
    private TextView title;
    private EditText firstname, lastname,email,phone,password,confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup = findViewById(R.id.signup);
        title = findViewById(R.id.title);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup) {
            register();
            finish();
        }
    }

    private void register(){
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
        else if( passwordStr.isEmpty()){
            displayToast("password field empty");
        }
        else  if(confirmPasswordStr.isEmpty()){
            displayToast("Sex field is empty ");
        }
        else if(!confirmPasswordStr.equals(passwordStr)){
            displayToast("passwords doesnt match ");
        }
        else{
            SignupPresenter presenter = new SignupPresenter();
            presenter.register(firstnameStr, lastnameStr, emailStr, phoneStr, passwordStr, confirmPasswordStr);
            startActivity(new Intent(SignUpActivity.this, HomeFragment.class));
            finish();
            //dbhelper.insertUser(u); here we will add the user to DB
            displayToast("Welcome to the FunRide Family !!");
            finish();
        }
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}