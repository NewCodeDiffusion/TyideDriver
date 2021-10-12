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
import com.codediffusion.tyidedriver.Model.modelCurrentOrder;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.adapter.AdapterDriverCurrentOder;
import com.codediffusion.tyidedriver.ui.Wallet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.DriverID;


public class CurrentFragment extends Fragment {
    private CompositeDisposable disposable=new CompositeDisposable();
  private TextView tv_CurrentBookingNotFound;
    private AdapterDriverCurrentOder adapterDriverCurrentOder;
    private List<modelCurrentOrder.Datum>currentOrder=new ArrayList<>();
    private RecyclerView rv_CurrentOrder;
    private String DRIVERID;

    private SharedPreferences sharedPreferences;


    public CurrentFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_current, container, false);

        sharedPreferences=getActivity().getSharedPreferences(CommonData.UserData, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(DriverID)){
            DRIVERID=sharedPreferences.getString(DriverID,"");
        }



       Initialization(view);
    return  view;
    }

    private void Initialization(View view) {

        tv_CurrentBookingNotFound=view.findViewById(R.id.tv_CurrentBookingNotFound);
        rv_CurrentOrder=view.findViewById(R.id.rv_CurrentOrder);





        LoadCurrentBookingData();


    }

    private void LoadCurrentBookingData() {

        currentOrder.clear();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("driver_id", DRIVERID);
         hashMap.put("booking_type", "2");


        disposable.add(GlobalClassApiCall.initRetrofit().CurrentOrder(hashMap)
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

                                        currentOrder.add(user.getData().get(i));
                                    }


                                    rv_CurrentOrder.setVisibility(View.VISIBLE);
                                   adapterDriverCurrentOder=new AdapterDriverCurrentOder(currentOrder,getActivity());
                                    rv_CurrentOrder.setAdapter(adapterDriverCurrentOder);
                                    adapterDriverCurrentOder.notifyDataSetChanged();

                                    tv_CurrentBookingNotFound.setVisibility(View.GONE);

                                } else {
                                    // Toast.makeText(activity, user.getMessage() + "", Toast.LENGTH_SHORT).show();

                                    tv_CurrentBookingNotFound.setVisibility(View.VISIBLE);
                                    rv_CurrentOrder.setVisibility(View.GONE);


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