package com.codediffusion.tyidedriver.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import android.widget.Toast;

import com.codediffusion.tyidedriver.ApiCalingRetrofit.GlobalClassApiCall;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.bottomnavigation.Home;
import com.codediffusion.tyidedriver.databinding.ActivityBankdetailsBinding;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;
import static com.codediffusion.tyidedriver.Extra.CommonData.showLoading;

public class Bankdetails extends AppCompatActivity implements PaymentResultListener {
    private ActivityBankdetailsBinding binding;

    private String BUserName, BankName, BAccountNo, BReAccountNo, BIfscCode,DriverID,PayAmount;
    ActionBar actionBar;
    private CompositeDisposable disposable = new CompositeDisposable();

    private SharedPreferences sharedPreferences;
    private Bankdetails activity;
    private Context context;


    ///rozerPay
    private Checkout chackout;
    private String razorpayKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bankdetails);

        actionBar = getSupportActionBar();
        actionBar.hide();

        context=activity=this;
        sharedPreferences=getSharedPreferences(CommonData.UserData,MODE_PRIVATE);
        if (sharedPreferences.contains(CommonData.DriverID)){
            DriverID=sharedPreferences.getString(CommonData.DriverID,"");
        }




        Initialization();


        changestatusbarcolor();


    }

    private void Initialization() {


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validation();
                /*Intent intent = new Intent(Bankdetails.this, Home.class);
                startActivity(intent);*/

            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void Validation() {
        BUserName = binding.etUserName.getText().toString();
        BankName = binding.etBankName.getText().toString();
        BAccountNo = binding.etAccountNo.getText().toString();
        BReAccountNo = binding.etReEnterAccountNO.getText().toString();
        BIfscCode = binding.etIfscCode.getText().toString();

        if (TextUtils.isEmpty(BUserName)) {
            binding.etUserName.setError("Enter User Name");
            binding.etUserName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(BankName)) {
            binding.etBankName.setError("Enter Bank Name");
            binding.etBankName.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(BAccountNo)) {
            binding.etAccountNo.setError("Enter Account No");
            binding.etAccountNo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(BReAccountNo)) {
            binding.etReEnterAccountNO.setError("Enter Re-Account NO");
            binding.etReEnterAccountNO.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(BIfscCode)) {
            binding.etIfscCode.setError("Enter IFSC Code");
            binding.etIfscCode.requestFocus();
            return;
        }

        if (!BAccountNo.equals(BReAccountNo)){
            Toast.makeText(activity, "Account No Not Match", Toast.LENGTH_SHORT).show();
            return;
        }

        CAllBankDetails();


    }

    private void CAllBankDetails() {

        showLoading(context, "Please Wait");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_id", DriverID);
        hashMap.put("account_number", BAccountNo);
        hashMap.put("account_holder_name", BUserName);
        hashMap.put("bank_name", BankName);
        hashMap.put("ifsc_code", BIfscCode);


        Log.e("params", hashMap + "");

///

        disposable.add(GlobalClassApiCall.initRetrofit().UserBenkDetailsData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((user, throwatble) -> {
                            hideLoading();
                            if (user != null) {

                                Log.e("jsdrikfhjkfh", "Response size: " + new Gson().toJson(user) + "");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(getApplicationContext(), user.getMessage() + "", Toast.LENGTH_LONG).show();
                                    /*Intent in = new Intent(Bankdetails.this, Home.class);
                                    startActivity(in);
                                    finishAffinity();*/

                                    String convertedAmount=String.valueOf(Integer.parseInt("500")*100);
                                    PayAmount="500";
                                    rezorpayCall("Qasim", "pasha@gmail.com", "8859228214", convertedAmount);
                                } else {
                                    Toast.makeText(getApplicationContext(),  "Account Number already added", Toast.LENGTH_SHORT).show();

                                }
                            }

                        }
                )
        );


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changestatusbarcolor() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) ;
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.lightgraan));
    }


    public void rezorpayCall(String name, String email, String phNo, String convertedAmount) {

        //CommonData.showLoading(context, "Please Wait");
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        // razorpayKey = "razorpayKey"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id
        //razorpayKey = "rzp_live_gcVN7HNkZIliDS"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id
        razorpayKey = "rzp_test_AyFjT1PXxj0piN"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id



        //razorpayKey = "rzp_live_z9P0CARvfz3lbX"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id


        chackout = new Checkout();
        chackout.setKeyID(razorpayKey);
        try {

            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "Razorpay Payment");
            options.put("currency", "INR");
            options.put("amount", convertedAmount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", phNo);
            options.put("prefill", preFill);

            chackout.open(Bankdetails.this, options);
        } catch (Exception e) {
            Toast.makeText(Bankdetails.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        // After successful payment Razorpay send back a unique id
        Toast.makeText(Bankdetails.this, "Transaction Successful: " + razorpayPaymentID, Toast.LENGTH_LONG).show();

        Log.e("kdljglk","Transaction Successful:"+razorpayPaymentID);

        Intent in = new Intent(Bankdetails.this, Home.class);
        startActivity(in);
        finishAffinity();

        //startActivity(new Intent(Fare_Detail_grActivity.this, ));

    }

    @Override
    public void onPaymentError(int i, String error) {
        // Error message
        Toast.makeText(Bankdetails.this, "Transaction Failed: "+ error , Toast.LENGTH_LONG).show();
    }




}