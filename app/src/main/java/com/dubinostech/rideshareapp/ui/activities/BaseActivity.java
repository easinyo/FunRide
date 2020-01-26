package com.dubinostech.rideshareapp.ui.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Emmanuel
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        init();
    }


    protected void showMessage(View container, String message)
    {
        Snackbar snackbar = Snackbar
                .make(container,message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        View view = snackbar.getView();
        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    protected void next(Bundle bundle, Class<?> activity,boolean destroy) {
        Intent intent= new Intent(this,activity);
        if(bundle!=null){intent.putExtras(bundle);}
        startActivity(intent);
        if(destroy)finish();
    }

    public abstract int getLayout();

    public abstract void init();
}
