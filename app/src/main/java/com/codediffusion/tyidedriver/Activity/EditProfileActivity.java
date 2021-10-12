package com.codediffusion.tyidedriver.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codediffusion.tyidedriver.ApiCalingRetrofit.GlobalClassApiCall;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.Extra.VolleyMultipartRequest;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.Register;
import com.codediffusion.tyidedriver.databinding.ActivityEditProfileBinding;
import com.codediffusion.tyidedriver.ui.Uploaddl;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.UserMobile;
import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;
import static com.codediffusion.tyidedriver.Extra.CommonData.showLoading;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    ActionBar actionBar;
    int PICK_IMAGE_GALLERY = 100;
    int PICK_IMAGE_CAMERA = 101;
    public Bitmap bitmap;
    private String DRIVERID;
    private String DName,DEmail,DFullAddress,DCity,DState,DPinCode;
    private CompositeDisposable disposable=new CompositeDisposable();


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);

        actionBar= getSupportActionBar();
        actionBar.hide();



        sharedPreferences=getSharedPreferences(CommonData.UserData, Context.MODE_PRIVATE);



        if (sharedPreferences.contains(CommonData.DriverID)){
            DRIVERID=sharedPreferences.getString(CommonData.DriverID,"");
        }




        Initialization();
    }

    private void Initialization() {

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        binding.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });


        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VAlidation();
            }
        });




        LoadDriverProfileData();



    }

    private void VAlidation() {

        DName=binding.etDriverName.getText().toString();
        DEmail=binding.etDriverEmain.getText().toString();
        DFullAddress=binding.etDriverCurrentAddress.getText().toString();
        DCity=binding.etDriverCurAddCity.getText().toString();
        DState=binding.etDriverCurAddState.getText().toString();
        DPinCode=binding.etPinCode.getText().toString();



        if (TextUtils.isEmpty(DName)){
            binding.etDriverName.setError("Enter Driver Name");
            binding.etDriverName.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(DEmail)){
            binding.etDriverEmain.setError("Enter Email Id");
            binding.etDriverEmain.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(DFullAddress)){
            binding.etDriverCurrentAddress.setError("Enter Full Address");
            binding.etDriverCurrentAddress.requestFocus();
            return;
        }




        if (TextUtils.isEmpty(DCity)){
            binding.etDriverCurAddCity.setError("Enter City Name");
            binding.etDriverCurAddCity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(DState)) {
            binding.etDriverCurAddState.setError("Enter State Name");
            binding.etDriverCurAddState.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(DPinCode)){
            binding.etPinCode.setError("Enter PinCode");
            binding.etPinCode.requestFocus();
            return;

        }


        if (bitmap==null){
            Toast.makeText(getApplicationContext(), "Upload Driver Image", Toast.LENGTH_SHORT).show();
            return;
        }


        UserProfileUpdateApi();



    }


    public boolean checkPermission() {

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : CommonData.Storage_Permision) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((EditProfileActivity.this),
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), 100);
            return false;
        } else {
            ChooseImageDialog();
        }
        return true;
    }

    private void ChooseImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(EditProfileActivity.this);
        builderSingle.setTitle("Choose Image");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(EditProfileActivity.this,
                        android.R.layout.simple_list_item_1);
        arrayAdapter.add("Camera");
        arrayAdapter.add("Gallery");

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                dialog.dismiss();
                try {
                    if (strName.equals("Gallery")) {


                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
                    } else {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);

                    }
                } catch (SecurityException e) {
                    Toast.makeText(getApplicationContext(), "Please allow permission to get image.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builderSingle.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                ChooseImageDialog();
            } else {

                ChooseImageDialog();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            Log.e("pathhhh", filePath.getPath() + "");
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                binding.ivUserImage.setImageBitmap(bitmap);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {
            try {

                bitmap = (Bitmap) data.getExtras().get("data");

                binding.ivUserImage.setImageBitmap(bitmap);


                Log.e("Image Path", "onActivityResult: ");
            } catch (Exception e) {

                e.printStackTrace();
            }
        }


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
                            hideLoading();
                            if (user != null) {

                                Log.e("jsdrikfhjkfh","Response size: " + new Gson().toJson(user)+"");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(getApplicationContext(), user.getMessage()+"", Toast.LENGTH_LONG).show();


                                 //   Glide.with(getApplicationContext()).load(user.getData().getPicture()).into(binding.ivUserImage);

                                    binding.etDriverName.setText(user.getData().getName());
                                    binding.etDriverEmain.setText(user.getData().getEmail());
                                   // binding.etPassCode.setText(user.getData().get());
                                    binding.etDriverCurrentAddress.setText(user.getData().getCurrentAddress());
                                    binding.etDriverCurAddCity.setText(user.getData().getCity());
                                    binding.etDriverCurAddState.setText(user.getData().getState());
                                    binding.etPinCode.setText(user.getData().getPincode());

                                }else{


                                    Toast.makeText(getApplicationContext(), user.getMessage()+"", Toast.LENGTH_SHORT).show();

                                }
                            }else{

                                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                )
        );



    }


    private void UserProfileUpdateApi() {
        showLoading(EditProfileActivity.this, "Please Wait");
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CommonData.UploadDriverProfileData, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.e("upload_response", response.data.toString() + "");
                hideLoading();
                try {
                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("200")) {


                        JSONObject jsonObject1=jsonObject.getJSONObject("data");

                        Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_SHORT).show();



                    } else {

                        Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();


                hashMap.put("driver_id", DRIVERID);
                hashMap.put("email", DEmail);
                hashMap.put("name", DName);
                hashMap.put("current_address", DFullAddress);
                hashMap.put("city", DCity);
                hashMap.put("state", DState);
                hashMap.put("pincode", DPinCode);


                Log.e("params",hashMap+"");

                return hashMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();

                params.put("picture", new DataPart(System.currentTimeMillis() + ".png", getFileDataFromDrawable(bitmap)));
                Log.e("PARAMS", params + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleyMultipartRequest.setShouldCache(true);
        requestQueue.add(volleyMultipartRequest);

    }

    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }





}