package com.codediffusion.tyidedriver.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codediffusion.tyidedriver.ApiCalingRetrofit.GlobalClassApiCall;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.Model.modelCompleteOrder;
import com.codediffusion.tyidedriver.Model.modelCurrentOrder;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.adapter.AdapterDriverCompleteOder;
import com.codediffusion.tyidedriver.adapter.AdapterDriverCurrentOder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.DriverID;


public class CompletedFragment extends Fragment {
    private CompositeDisposable disposable=new CompositeDisposable();
    private TextView tv_CurrentBookingNotFound;
    private AdapterDriverCompleteOder adapterCompleteOrder;
    private List<modelCompleteOrder.Datum> CompleteOrder=new ArrayList<>();
    private RecyclerView rv_CompleteOrder;
    private String DRIVERID;

    private SharedPreferences sharedPreferences;


    public CompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getActivity(), "Qasim", Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_completed, container, false);


        sharedPreferences=getActivity().getSharedPreferences(CommonData.UserData, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(DriverID)){
            DRIVERID=sharedPreferences.getString(DriverID,"");
        }



        Initialization(view);
        return  view;

    }


    private void Initialization(View view) {

        tv_CurrentBookingNotFound=view.findViewById(R.id.tv_CurrentBookingNotFound);
        rv_CompleteOrder=view.findViewById(R.id.rv_CompleteOrder);





        LoadCompleteBookingData();


    }

    private void LoadCompleteBookingData() {

        CompleteOrder.clear();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("driver_id", DRIVERID);
        hashMap.put("booking_type", "4");


        disposable.add(GlobalClassApiCall.initRetrofit().CompletetOrder(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((user, throwatble) -> {
                            // hideLoading();
                            if (user != null) {

                                Log.e("jksdhkfhk", "Response size: " + new Gson().toJson(user) + "");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(getActivity(), user.getMessage() + "", Toast.LENGTH_LONG).show();

                                    for (int i=0;i<user.getData().size();i++){

                                        CompleteOrder.add(user.getData().get(i));
                                    }


                                    rv_CompleteOrder.setVisibility(View.VISIBLE);
                                    adapterCompleteOrder=new AdapterDriverCompleteOder(CompleteOrder,getActivity());
                                    rv_CompleteOrder.setAdapter(adapterCompleteOrder);
                                    adapterCompleteOrder.notifyDataSetChanged();

                                    tv_CurrentBookingNotFound.setVisibility(View.GONE);

                                } else {
                                    // Toast.makeText(activity, user.getMessage() + "", Toast.LENGTH_SHORT).show();

                                    tv_CurrentBookingNotFound.setVisibility(View.VISIBLE);
                                    rv_CompleteOrder.setVisibility(View.GONE);


                                }
                            } else {
                                //hideLoading();
                                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                )
        );



    }


}