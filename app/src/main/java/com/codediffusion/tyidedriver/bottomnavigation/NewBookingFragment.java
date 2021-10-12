package com.codediffusion.tyidedriver.bottomnavigation;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.codediffusion.tyidedriver.ApiCalingRetrofit.GlobalClassApiCall;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.Model.modelNewBooking;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.adapter.AdapterNewBooking;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;
import static com.codediffusion.tyidedriver.Extra.CommonData.showLoading;

public class NewBookingFragment extends Fragment implements AdapterNewBooking.UpdateList {
    private LinearLayout ll_UserUnVerified,ll_UserVerified;
    private CompositeDisposable disposable=new CompositeDisposable();
    private SharedPreferences sharedPreferences;
    private String DriverId;
    private Dialog dialog;
    private TextView tv_TotalEarned,tv_BokingBotFound;

    private RecyclerView rv_newBooking;

    private AdapterNewBooking adapterNewBooking;
    private List<modelNewBooking.Datum>newBookingList=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_status, container, false);
        super.onCreate(savedInstanceState);


        return rootView;
    }




    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedPreferences=getActivity().getSharedPreferences(CommonData.UserData,Context.MODE_PRIVATE);

        if (sharedPreferences.contains(CommonData.DriverID)){
             DriverId=sharedPreferences.getString(CommonData.DriverID,"");
        }


        Initialization(view);

    }

    private void Initialization(View view) {

        ll_UserVerified=view.findViewById(R.id.ll_UserVerified);
        rv_newBooking=view.findViewById(R.id.rv_newBooking);
        tv_TotalEarned=view.findViewById(R.id.tv_TotalEarned);
        tv_BokingBotFound=view.findViewById(R.id.tv_BokingBotFound);



        tv_TotalEarned.setText(getString(R.string.Rs)+" "+"8562");

        CheckStatusApiData();


       LoadNewBookingApiData();

        OpenInsuranceDialog();
    }

    private void LoadNewBookingApiData() {
        newBookingList.clear();

       // showLoading(getActivity(), "Please Wait");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("booking_type", "1");



        Log.e("params",hashMap+"");

///

        disposable.add(GlobalClassApiCall.initRetrofit().DriverNewBookingData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((user, throwatble) -> {
                            hideLoading();
                            if (user != null) {

                                Log.e("jsdrikfhjkfh","Response size: " + new Gson().toJson(user)+"");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(getActivity(), user.getMessage()+"", Toast.LENGTH_LONG).show();


                                    for (int i=0;i<user.getData().size();i++){
                                        newBookingList.add(user.getData().get(i));

                                    }

                                    rv_newBooking.setVisibility(View.VISIBLE);
                                    adapterNewBooking=new AdapterNewBooking(newBookingList,getActivity(),NewBookingFragment.this);
                                    rv_newBooking.setAdapter(adapterNewBooking);
                                    adapterNewBooking.notifyDataSetChanged();


                                    tv_BokingBotFound.setVisibility(View.GONE);
                                }else{
                                    rv_newBooking.setVisibility(View.GONE);
                                    tv_BokingBotFound.setVisibility(View.VISIBLE);

                                    Toast.makeText(getActivity(), user.getMessage()+"", Toast.LENGTH_SHORT).show();

                                }
                            }else{

                                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                )
        );



    }

    private void CheckStatusApiData() {
        showLoading(getActivity(), "Please Wait");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_id", DriverId);



        Log.e("params",hashMap+"");

///

        disposable.add(GlobalClassApiCall.initRetrofit().UserStatusData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((user, throwatble) -> {
                            hideLoading();
                            if (user != null) {

                                Log.e("jsdrikfhjkfh","Response size: " + new Gson().toJson(user)+"");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(getActivity(), user.getMessage()+"", Toast.LENGTH_LONG).show();


                                    ll_UserVerified.setVisibility(View.VISIBLE);





                                }else{
                                    Toast.makeText(getActivity(), user.getMessage()+"", Toast.LENGTH_SHORT).show();
                                   // ll_UserUnVerified.setVisibility(View.VISIBLE);
                                    OpenAlertDialog();
                                    ll_UserVerified.setVisibility(View.GONE);
                                }
                            }else{
                                OpenAlertDialog();
                               // ll_UserUnVerified.setVisibility(View.VISIBLE);
                                ll_UserVerified.setVisibility(View.GONE);


                                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                )
        );

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    private void OpenAlertDialog() {

         dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_joinlive_streaming);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        dialog.show();


    }


    private void OpenInsuranceDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_insurance);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        Button btn_Buy=(Button)dialog.findViewById(R.id.btn_Buy);

        btn_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Your Request Send SuccessFully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });




        dialog.show();


    }


    @Override
    public void UpdateData() {

        LoadNewBookingApiData();
    }
}