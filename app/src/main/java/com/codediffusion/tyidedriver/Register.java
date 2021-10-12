package com.codediffusion.tyidedriver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.Extra.VolleyMultipartRequest;
import com.codediffusion.tyidedriver.databinding.ActivityRegisterBinding;
import com.codediffusion.tyidedriver.ui.Uploaddl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

import static com.codediffusion.tyidedriver.Extra.CommonData.UserMobile;
import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;
import static com.codediffusion.tyidedriver.Extra.CommonData.showLoading;

public class Register extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private String DName,DMobile,DEmail,DPass,UserID,DCurrentFullAddress="",DCurrentAddressCity="",DCurrentAddressState="",
            DCurrentAddressPinCode="",DPermanentFullAddress="",DPermanentAddressCity="",DPermanentAddressState="",DPermanentAddressPinCode="",DCofirmPass="",
            DPassCode="",Id,UserFirebaseToken;
    private CompositeDisposable disposable=new CompositeDisposable();
  private Register activity;
    private Context context;

    private TextView loogin;
    private ActionBar  actionBar;
    private Button next;

    public boolean SelectCB=false;
    int PICK_IMAGE_GALLERY = 100;
    int PICK_IMAGE_CAMERA = 101;
    public Bitmap bitmap;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_register);

           sharedPreferences=getSharedPreferences(CommonData.UserData,MODE_PRIVATE);
           editor=sharedPreferences.edit();

        context=activity=this;




        actionBar = getSupportActionBar();
        actionBar.hide();



       /* FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()){
                    token= task.getResult().getToken();
                    Log.e("lkdfjgk",token+"");
                }
            }
        });*/







        Initialization();





    }

    private void Initialization() {

        changestatusbarcolor();

        loogin = findViewById(R.id.loginhere);
        loogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);

            }
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();

               /* Intent intent = new Intent(Register.this, Uploaddl.class);
                startActivity(intent);*/

            }
        });



          binding.cbPerAddress.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  if (binding.cbPerAddress.isChecked()){
                      binding.llPermanentAddress.setVisibility(View.VISIBLE);
                  }else{
                      binding.llPermanentAddress.setVisibility(View.GONE);
                  }
              }
          });


       /* binding.cbPerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cbPerAddress.setChecked(true);
                binding.llPermanentAddress.setVisibility(View.VISIBLE);
              *//*  if (SelectCB=false){

                    //SelectCB=false;
                }else{
                    binding.llPermanentAddress.setVisibility(View.GONE);
                }*//*
            }
        });*/
        binding.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });


        database = FirebaseDatabase.getInstance();

        FirebaseMessaging.getInstance()
                .getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String token) {


                        UserFirebaseToken = token;

                        Log.e("klsjhgfghj", UserFirebaseToken + "");


                    }
                });







    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changestatusbarcolor(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.lightgraan));
    }



    private void Validation() {

        DName=binding.etDriverName.getText().toString();
        DEmail=binding.etDriverEmain.getText().toString();
        DMobile=binding.etDriverMobileNo.getText().toString();
        DCurrentFullAddress=binding.etDriverCurrentAddress.getText().toString();
        DCurrentAddressCity=binding.etDriverCurAddCity.getText().toString();
        DCurrentAddressState=binding.etDriverCurAddState.getText().toString();
        DCurrentAddressPinCode=binding.etDriverCurAddPinCode.getText().toString();
        DPermanentFullAddress=binding.etDriverPermanentAddress.getText().toString();
        DPermanentAddressCity=binding.etDriverPerAddCity.getText().toString();
        DPermanentAddressState=binding.etDriverPerAddState.getText().toString();
        DPermanentAddressPinCode=binding.etDriverPerAddPinCode.getText().toString();
        DPass=binding.etDriverPassWord.getText().toString();
        DCofirmPass=binding.etDriverConfirmPassword.getText().toString();
        DPassCode=binding.etDriverPassCode.getText().toString();

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

        if (TextUtils.isEmpty(DMobile)){
            binding.etDriverMobileNo.setError("Enter Mobile No");
            binding.etDriverMobileNo.requestFocus();
            return;
        }

        if (DMobile.length()<10){
            binding.etDriverMobileNo.setError("Mobile No Must be 10 Digits");
            binding.etDriverMobileNo.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(DCurrentFullAddress)){
            binding.etDriverCurrentAddress.setError("Enter Current Address");
            binding.etDriverCurrentAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(DCurrentAddressCity)){
            binding.etDriverCurAddCity.setError("Enter Current Address City");
            binding.etDriverCurAddCity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(DCurrentAddressState)){
            binding.etDriverCurAddState.setError("Enter Current Address State");
            binding.etDriverCurAddState.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(DCurrentAddressPinCode)){
            binding.etDriverCurAddPinCode.setError("Enter Current Address PinCode");
            binding.etDriverCurAddPinCode.requestFocus();
            return;
        }

        /*if (binding.cbPerAddress.isChecked()){
            if (TextUtils.isEmpty(DPermanentFullAddress)){
                binding.etDriverPermanentAddress.setError("Enter Permanent Address");
                binding.etDriverPermanentAddress.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(DPermanentAddressCity)){
                binding.etDriverPerAddCity.setError("Enter Permanent Address City");
                binding.etDriverPerAddCity.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(DPermanentAddressState)){
                binding.etDriverPerAddState.setError("Enter Permanent Address State");
                binding.etDriverPerAddState.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(DPermanentAddressPinCode)){
                binding.etDriverPerAddPinCode.setError("Enter Current Permanent PinCode");
                binding.etDriverPerAddPinCode.requestFocus();
                return;
            }
        }*/



        if (TextUtils.isEmpty(DPass)){
            binding.etDriverPassWord.setError("Enter Password");
            binding.etDriverPassWord.requestFocus();
            return;
        }

        if (DPass.length()<4){
            binding.etDriverPassWord.setText("PassWord Must be 4 Digits");
            binding.etDriverPassWord.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(DCofirmPass)){
            binding.etDriverConfirmPassword.setError("Enter Confirm Password");
            binding.etDriverConfirmPassword.requestFocus();
            return;
        }

        if (DCofirmPass.length()<4){
            binding.etDriverConfirmPassword.setText("Confirm PassWord Must be 4 Digits");
            binding.etDriverConfirmPassword.requestFocus();
            return;
        }

        if (!DPass.equals(DCofirmPass)){
            Toast.makeText(activity, "Password Or Confirm Password Not Match", Toast.LENGTH_SHORT).show();
       return;
        }


        if (TextUtils.isEmpty(DPassCode)){
            binding.etDriverPassCode.setError("Enter PassCode");
            binding.etDriverPassCode.requestFocus();
            return;
        }
        if (bitmap==null){
            Toast.makeText(activity, "Upload Driver Image", Toast.LENGTH_SHORT).show();
            return;
        }

        /*Intent intent = new Intent(Register.this, Uploaddl.class);
        startActivity(intent);*/



        UserRegisterDataApi();

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
            ActivityCompat.requestPermissions((activity),
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), 100);
            return false;
        } else {
            ChooseImageDialog();
        }
        return true;
    }

    private void ChooseImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle("Choose Image");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(activity,
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
                    Toast.makeText(context, "Please allow permission to get image.", Toast.LENGTH_SHORT).show();
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

    private void UserRegisterDataApi() {
        showLoading(context, "Please Wait");
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CommonData.UserRagisterData, new Response.Listener<NetworkResponse>() {
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

                        Toast.makeText(activity, message + "", Toast.LENGTH_SHORT).show();

                         Id=jsonObject1.getString("driver_id");

                        Intent intent = new Intent(Register.this, Uploaddl.class);
                        startActivity(intent);
                       // finishAffinity();


                        editor.putString(UserMobile,DMobile);
                        //editor.putString(UserName,DName);
                        //editor.putString(UserEmail,DEmail);
                       editor.putString(CommonData.DriverID,Id);
                        editor.apply();
                        editor.commit();


                    } else {

                        Toast.makeText(activity, message + "", Toast.LENGTH_SHORT).show();

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

                hashMap.put("user_mobile", DMobile);
                hashMap.put("user_email", DEmail);
                hashMap.put("user_name", DName);
                hashMap.put("permanent_address", DPermanentFullAddress);
                hashMap.put("otp_status", "2");
                hashMap.put("firebase_token", UserFirebaseToken);
                hashMap.put("current_address", DCurrentFullAddress);
                hashMap.put("city", DCurrentAddressCity);
                hashMap.put("state", DCurrentAddressState);
                hashMap.put("pincode", DCurrentAddressPinCode);
                hashMap.put("password", DPass);
                hashMap.put("passcode", DPassCode);
                hashMap.put("p_city", DPermanentAddressCity);
                hashMap.put("p_state", DPermanentAddressState);
                hashMap.put("p_pincode", DPermanentAddressPinCode);

                Log.e("params",hashMap+"");

                return hashMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();

                params.put("user_img", new DataPart(System.currentTimeMillis() + ".png", getFileDataFromDrawable(bitmap)));
                Log.e("PARAMS", params + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
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