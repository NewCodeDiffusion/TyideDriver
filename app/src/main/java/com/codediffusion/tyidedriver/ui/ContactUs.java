package com.codediffusion.tyidedriver.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.bottomnavigation.Home;

public class ContactUs extends AppCompatActivity {

    ActionBar actionBar;


    ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);




        ivBack = findViewById(R.id.btnBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();

            }
        });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendEmail();
            }
        }, 3000);

        actionBar=getSupportActionBar();
        actionBar.hide();

        TextView textView=findViewById(R.id.mail);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail();

            }
        });


    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    
    private void sendEmail(){


        Intent email = new Intent(Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto:contact@finhealcapital.in"));

        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"Care@tyidedriver.in"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Contacting Tyide");
        email.putExtra(Intent.EXTRA_TEXT, "Hey Tyide \n\nMy Name is :");

        //need this to prompts email client only

        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}