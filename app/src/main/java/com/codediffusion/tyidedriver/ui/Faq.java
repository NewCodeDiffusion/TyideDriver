package com.codediffusion.tyidedriver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.bottomnavigation.Home;
import com.github.florent37.expansionpanel.ExpansionLayout;

public class Faq extends AppCompatActivity {

    ActionBar actionBar;
    ExpansionLayout expansionLayout,expansionLayout2,expansionLayout3,expansionLayout4,expansionLayout5,expansionLayout6,expansionLayout7,expansionLayout8,expansionLayout9;


    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        actionBar=getSupportActionBar();
        actionBar.hide();


        ivBack = findViewById(R.id.btnBack);



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();

            }
        });



        expansionLayout = findViewById(R.id.expansionLayout);
        expansionLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        expansionLayout2 = findViewById(R.id.expansionLayout2);
        expansionLayout2.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        expansionLayout3 = findViewById(R.id.expansionLayout3);
        expansionLayout3.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        expansionLayout4 = findViewById(R.id.expansionLayout4);
        expansionLayout4.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        expansionLayout5 = findViewById(R.id.expansionLayout5);
        expansionLayout5.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        expansionLayout6 = findViewById(R.id.expansionLayout6);
        expansionLayout6.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        expansionLayout7 = findViewById(R.id.expansionLayout7);
        expansionLayout7.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });
        expansionLayout8 = findViewById(R.id.expansionLayout8);
        expansionLayout8.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        expansionLayout9 = findViewById(R.id.expansionLayout9);
        expansionLayout9.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

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

}