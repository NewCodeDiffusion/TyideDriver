package com.codediffusion.tyidedriver.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.adapter.ViewPagerAdapter;
import com.codediffusion.tyidedriver.bottomnavigation.Home;
import com.codediffusion.tyidedriver.ui.fragment.CompletedFragment;
import com.codediffusion.tyidedriver.ui.fragment.CurrentFragment;
import com.codediffusion.tyidedriver.ui.fragment.CancelledFragment;
import com.google.android.material.tabs.TabLayout;

public class Order extends AppCompatActivity {

    ActionBar actionBar;


    ImageView ivBack;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

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

        viewPager = findViewById(R.id.viewPager);

        addTabs(viewPager);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager( viewPager );


    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CurrentFragment(), "Current");
        adapter.addFrag(new CompletedFragment(), "Completed");
        adapter.addFrag(new CancelledFragment(), "Cancelled");
        viewPager.setAdapter(adapter);
    }
}