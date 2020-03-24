package com.dubinostech.rideshareapp.ui.view;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

/*
 * The class Terms Activity Displays Fun-Ride Terms and condition of use

 * */
import com.dubinostech.rideshareapp.R;

public class Terms extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        WebView webView = findViewById(R.id.termsview);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        String termsPDF = "https://fun-ride.herokuapp.com/terms.pdf";
        String url = "https://drive.google.com/viewerng/viewer?embedded=true&url="+termsPDF;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                getSupportActionBar().setTitle("Loading...");
                super.onProgressChanged(view, newProgress);
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                    getSupportActionBar().setTitle(R.string.app_name);
                }
            }
        });

        webView.loadUrl(url);


    }
}
