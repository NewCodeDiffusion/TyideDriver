package com.codediffusion.tyidedriver.bottomnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.codediffusion.tyidedriver.Activity.EditProfileActivity;
import com.codediffusion.tyidedriver.ApiCalingRetrofit.GlobalClassApiCall;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.Login;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.adapter.AdapterNewBooking;
import com.codediffusion.tyidedriver.ui.AboutUs;
import com.codediffusion.tyidedriver.ui.ContactUs;
import com.codediffusion.tyidedriver.ui.Faq;
import com.codediffusion.tyidedriver.ui.Order;
import com.codediffusion.tyidedriver.ui.PrivacyPolicy;
import com.codediffusion.tyidedriver.ui.Termsofservice;
import com.codediffusion.tyidedriver.ui.Wallet;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;


public class ProfileFragment extends Fragment {
    private ImageView iv_editProfile,iv_driverImage;
    private TextView tvnameHeading, tvemail, tvPhone,tv_rating;
    private Button btn_Logout;
    private String DRIVERID;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private CompositeDisposable disposable=new CompositeDisposable();

    Context context;


    public ProfileFragment() {
        // Required empty public constructor
    }

    String[] listviewTitle = new String[]{
            /*"Personal Details", "My Loans", "Payment History", "Preferred Language",*/
            "Wallet", "All Order", "Privacy Policy", "Terms & Condition", "FAQ's", "About Us", "Contact Us", "Rate Us"
    };

    int[] listviewImage = new int[]{
            /*    R.drawable.ic_fci_profile, R.drawable.ic_fci_myloan, R.drawable.ic_fci_payment,
                R.drawable.ic_fci_translate,*/
            R.drawable.ic_fci_terms, R.drawable.ic_fci_terms, R.drawable.ic_fci_terms, R.drawable.ic_fci_terms,
            R.drawable.ic_fci_faq, R.drawable.ic_fci_aboutus, R.drawable.ic_fci_contactus, R.drawable.ic_fci_rateus,
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        context = getActivity();


        sharedPreferences=getActivity().getSharedPreferences(CommonData.UserData,Context.MODE_PRIVATE);

        editor=sharedPreferences.edit();

        if (sharedPreferences.contains(CommonData.DriverID)){
            DRIVERID=sharedPreferences.getString(CommonData.DriverID,"");
        }




        Initialization(view);





        return view;
    }

    private void Initialization(View view) {


        iv_driverImage = view.findViewById(R.id.iv_driverImage);
        tvemail = view.findViewById(R.id.tv_email);
        tvPhone = view.findViewById(R.id.tv_Phone);
        btn_Logout = view.findViewById(R.id.btn_Logout);
        iv_editProfile = view.findViewById(R.id.iv_editProfile);
        tvnameHeading = view.findViewById(R.id.tvNameheading);
        tv_rating = view.findViewById(R.id.tv_rating);



        iv_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });



        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 6; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title"};
        int[] to = {R.id.listview_image, R.id.listview_item_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.listview_activity, from, to);
        ListView androidListView = (ListView) view.findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (androidListView.getPositionForView(view) == 0) {
                    Intent in = new Intent(getActivity(), Wallet.class);
                    startActivity(in);
                } else if (androidListView.getPositionForView(view) == 1) {
                    Intent in = new Intent(getActivity(), Order.class);
                    startActivity(in);
                } else if (androidListView.getPositionForView(view) == 2) {
                    Intent in = new Intent(getActivity(), PrivacyPolicy.class);
                    startActivity(in);
                } else if (androidListView.getPositionForView(view) == 3) {
                    Intent in = new Intent(getActivity(), Termsofservice.class);
                    startActivity(in);
                } else if (androidListView.getPositionForView(view) == 4) {
                    Intent in = new Intent(getActivity(), Faq.class);
                    startActivity(in);
                } else if (androidListView.getPositionForView(view) == 5) {
                    Intent in = new Intent(getActivity(), AboutUs.class);
                    startActivity(in);
                } else if (androidListView.getPositionForView(view) == 6) {
                    Intent in = new Intent(getActivity(), ContactUs.class);
                    startActivity(in);
                } else if (androidListView.getPositionForView(view) == 7) {
                    //Toast.makeText(getActivity(), "Oups ! Some Error Occoured !", Toast.LENGTH_SHORT).show();
//                    init();


                } else {


                }

            }
        });


        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent intent = new Intent(getActivity(), Login.class);
                                startActivity(intent);

                                editor.clear();
                                editor.apply();

                              //  drawerLayout.closeDrawers();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                //drawerLayout.closeDrawers();
            }
        });


        LoadDriverProfileData();

    }

    private void LoadDriverProfileData() {
        // showLoading(getActivity(), "Please Wait");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("driver_id", DRIVERID);



        Log.e("params",hashMap+"");

///

        disposable.add(GlobalClassApiCall.initRetrofit().GetProfileData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((user, throwatble) -> {
                          //  hideLoading();
                            if (user != null) {

                                Log.e("jsdrikfhjkfh","Response size: " + new Gson().toJson(user)+"");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(getActivity(), user.getMessage()+"", Toast.LENGTH_LONG).show();


                                    Glide.with(context).load(user.getData().getPicture()).into(iv_driverImage);

                                    tvnameHeading.setText(user.getData().getName());
                                    tvemail.setText(user.getData().getEmail());
                                    tvPhone.setText(user.getData().getMobile());
                                    tv_rating.setText(user.getData().getRating());

                                }else{


                                    Toast.makeText(getActivity(), user.getMessage()+"", Toast.LENGTH_SHORT).show();

                                }
                            }else{

                                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                )
        );



    }

    @Override
    public void onResume() {
        super.onResume();

        LoadDriverProfileData();

    }
}