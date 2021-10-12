package com.codediffusion.tyidedriver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.bottomnavigation.Home;
import com.codediffusion.tyidedriver.bottomnavigation.LottieDialogFragment;

import butterknife.BindView;


public class AboutUs extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webView;
    LottieDialogFragment mDialogFragment;
    ActionBar actionBar;


   ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        actionBar = getSupportActionBar();
        actionBar.hide();
        ivBack = findViewById(R.id.btnBack);
        webView=findViewById(R.id.webview);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();

            }
        });

        String url = "https://www.privacypolicies.com/blog/privacy-policy-template/#:~:text=A%20Privacy%20Policy%20is%20a,or%20sold%20to%20third%20parties.";
        if (url.equals(null) || url.isEmpty()) {

            Toast.makeText(this, "Oups ! Some Error Occoured", Toast.LENGTH_SHORT).show();
        } else {

            showProgressDialog();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setHorizontalScrollBarEnabled(false);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    showProgressDialog();
                    view.loadUrl(url);

                    return true;
                }

                @Override
                public void onPageFinished(WebView view, final String url) {
                    hideProgressDialog();
                }
            });


            // webView.loadData(data, "text/html; charset=UTF-8;", null);
            webView.loadUrl(url);
        }
    }

        private void showProgressDialog(){

            mDialogFragment = new LottieDialogFragment();
            mDialogFragment.show(getSupportFragmentManager(),"");
        }

        private void hideProgressDialog(){
            mDialogFragment.dismiss();
        }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
