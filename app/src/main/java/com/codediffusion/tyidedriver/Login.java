package com.codediffusion.tyidedriver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codediffusion.tyidedriver.ApiCalingRetrofit.GlobalClassApiCall;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.bottomnavigation.Home;
import com.google.gson.Gson;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;
import static com.codediffusion.tyidedriver.Extra.CommonData.showLoading;

public class Login extends AppCompatActivity {

    private CompositeDisposable disposable=new CompositeDisposable();
    private ActionBar  actionBar;
    private TextView account;
    private Button next;
    private EditText et_mobileNo,et_Password;
    private String Umobile,UPass,Driver_Id;

    private Login activity;
    private Context context;

    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        actionBar = getSupportActionBar();
        actionBar.hide();
        changestatusbarcolor();

        context=activity=this;

        sharedPreferences=getSharedPreferences(CommonData.UserData,MODE_PRIVATE);

        editor=sharedPreferences.edit();


        if (sharedPreferences.contains(CommonData.DriverID)){
            Driver_Id=sharedPreferences.getString(CommonData.DriverID,"");

            Log.e("lkjhgfd",Driver_Id+"");

                Intent i = new Intent(this, Home.class);
                startActivity(i);
                finishAffinity();

        }



        Initialization();







    }

    private void Initialization() {

        et_mobileNo=findViewById(R.id.et_mobileNo);
        et_Password=findViewById(R.id.et_Password);

        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this, Register.class);
                startActivity(in);
            }
        });
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VAlidation();

                /*Intent in = new Intent(Login.this, Home.class);
                startActivity(in);*/
            }
        });



    }

    private void VAlidation() {

        Umobile=et_mobileNo.getText().toString();
        UPass=et_Password.getText().toString();


        if (TextUtils.isEmpty(Umobile)){
            et_mobileNo.setError("Enter Mobile No");
            et_mobileNo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(UPass)){
            et_Password.setError("Enter Password");
            et_Password.requestFocus();
            return;
        }


        CallLoginApi();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changestatusbarcolor(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.lightgraan));
    }


    private void CallLoginApi() {
        showLoading(context, "Please Wait");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("mobile", Umobile);
        hashMap.put("password", UPass);


        Log.e("params",hashMap+"");

///

        disposable.add(GlobalClassApiCall.initRetrofit().UserLoginData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((user, throwatble) -> {
                            hideLoading();
                            if (user != null) {

                                Log.e("jsdrikfhjkfh","Response size: " + new Gson().toJson(user)+"");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(activity, user.getMessage()+"", Toast.LENGTH_LONG).show();

                                    String UserId=user.getData().getDriverId();
                                    Log.e("lkjhgf",UserId+"");

                                    editor.putString(CommonData.UserMobile,Umobile);
                                    //editor.putString(CommonData.DriverID,"user.getData().getUserId()");
                                    editor.putString(CommonData.DriverID,UserId);
                                    editor.apply();
                                    editor.commit();

                                    Intent in = new Intent(Login.this, Home.class);
                                    startActivity(in);
                                    finishAffinity();

                                }else{
                                    Toast.makeText(activity, user.getMessage()+"", Toast.LENGTH_SHORT).show();

                                }
                            }else{
                                Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                )
        );



    }




}