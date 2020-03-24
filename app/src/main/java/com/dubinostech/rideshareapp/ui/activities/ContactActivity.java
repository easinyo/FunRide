/*
* The class Contact Activity Allows Fun-Ride user to contact the support
* By sending name, email and a message
* */
package com.dubinostech.rideshareapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.repository.Data.LoggedUser;
import com.dubinostech.rideshareapp.repository.Libraries.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    private Button email;
    private EditText your_name, your_email, your_subject, your_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        your_name = findViewById(R.id.your_name);
        your_email = findViewById(R.id.your_email);
        your_subject = findViewById(R.id.your_subject);
        your_message = findViewById(R.id.your_message);
        email = findViewById(R.id.post_message);
        your_name.setText(LoggedUser.firstname+" "+LoggedUser.lastname);
        your_email.setText(LoggedUser.email);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.post_message) {
            sendEmail();
        }
    }

    // validating email id

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void sendEmail(){
        String name = your_name.getText().toString();
        String email = your_email.getText().toString();
        String subject = your_subject.getText().toString();
        String message = your_message.getText().toString();
        if (TextUtils.isEmpty(name)) {
            your_name.setError("Enter Your Name");
            your_name.requestFocus();
            return;
        }

        Boolean onError = false;
        if (!Utils.isValidEmail(email)) {
            onError = true;
            your_email.setError("Invalid Email");
            return;
        }

        if (TextUtils.isEmpty(subject)) {
            your_subject.setError("Enter Your Subject");
            your_subject.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(message)) {
            your_message.setError("Enter Your Message");
            your_message.requestFocus();
            return;
        }

        Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

        /* Fill it with Data */
        sendEmail.setType("plain/text");
        sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"bkano020@uottawa.ca"});
        sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                "name:" + name + '\n' + "Email ID:" + email + '\n' + "Message:" + '\n' + message);

        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(sendEmail, "Send mail..."));
    }

}