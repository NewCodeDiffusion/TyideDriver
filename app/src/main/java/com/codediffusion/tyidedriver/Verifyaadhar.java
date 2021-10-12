package com.codediffusion.tyidedriver;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.codediffusion.tyidedriver.ui.Bankdetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;
import static com.codediffusion.tyidedriver.Extra.CommonData.showLoading;

public class Verifyaadhar extends AppCompatActivity {


    private Button loogin;
    private ActionBar actionBar;
    private ImageView iv_AaadhaarFront,iv_AaadhaarBack,img_ADF,img_ADB;
    private String SelectImageType,DriverID,AadhaarNo="";
    private EditText et_AadhaarCardNo;


    private Bitmap AadhaaarFrontBitmap,AadhaaarBackBitmap;
    private int PICK_IMAGE_GALLERY = 100;
    private int PICK_IMAGE_CAMERA = 101;
    private Bitmap bitmap;
    private ImageView btnBack;
    private SharedPreferences sharedPreferences;
    private Verifyaadhar activity;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyaadhar);
        actionBar =getSupportActionBar();
        actionBar.hide();

        context=activity=this;

        sharedPreferences=getSharedPreferences(CommonData.UserData,MODE_PRIVATE);
        if (sharedPreferences.contains(CommonData.DriverID)){
            DriverID=sharedPreferences.getString(CommonData.DriverID,"");
        }


        changestatusbarcolor();


        Initialization();

    }

    private void Initialization() {

        loogin = findViewById(R.id.next);
        btnBack = findViewById(R.id.btnBack);
        iv_AaadhaarFront = findViewById(R.id.iv_AaadhaarFront);
        iv_AaadhaarBack = findViewById(R.id.iv_AaadhaarBack);
        img_ADF = findViewById(R.id.img_ADF);
        img_ADB = findViewById(R.id.img_ADB);
        et_AadhaarCardNo = findViewById(R.id.et_AadhaarCardNo);
        loogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VAlidation();
               /* Intent intent = new Intent(Verifyaadhar.this, Bankdetails.class);
                startActivity(intent);*/

            }
        });


        img_ADF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageType="AadhaarFront";
                ChooseImageDialog(SelectImageType);
            }
        });

        img_ADB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageType="AadhaarBack";
                ChooseImageDialog(SelectImageType);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void VAlidation() {
        AadhaarNo=et_AadhaarCardNo.getText().toString();

        if (TextUtils.isEmpty(AadhaarNo)){
            et_AadhaarCardNo.setError("Enter Aadhaar No");
            et_AadhaarCardNo.requestFocus();
            return;
        }
        if (AadhaaarFrontBitmap==null){
            Toast.makeText(getApplicationContext(), "Upload  Front Image", Toast.LENGTH_SHORT).show();
            return;
        }
        if (AadhaaarBackBitmap==null){
            Toast.makeText(getApplicationContext(), "Upload  Back Image", Toast.LENGTH_SHORT).show();
            return;
        }

        UploadAddhaarDataApi();


    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changestatusbarcolor(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.lightgraan));
    }

    private void ChooseImageDialog(String DocumentType) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Verifyaadhar.this);
        builderSingle.setTitle("Choose Image");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(Verifyaadhar.this,
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

                //ChooseImageDialog();

                ChooseImageDialog(SelectImageType);

            } else {

                ChooseImageDialog(SelectImageType);
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
                /// binding.userProfileImage.setImageBitmap(bitmap);

                if (SelectImageType.equalsIgnoreCase("AadhaarFront")){
                    iv_AaadhaarFront.setImageBitmap(bitmap);
                    AadhaaarFrontBitmap=bitmap;
                }

                if (SelectImageType.equalsIgnoreCase("AadhaarBack")){
                    iv_AaadhaarBack.setImageBitmap(bitmap);
                    AadhaaarBackBitmap=bitmap;
                }






            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {
            try {

                bitmap = (Bitmap) data.getExtras().get("data");


                if (SelectImageType.equalsIgnoreCase("AadhaarFront")){
                    iv_AaadhaarFront.setImageBitmap(bitmap);
                    AadhaaarFrontBitmap=bitmap;
                }

                if (SelectImageType.equalsIgnoreCase("AadhaarBack")){
                    iv_AaadhaarBack.setImageBitmap(bitmap);
                    AadhaaarBackBitmap=bitmap;
                }



                Log.e("Image Path", "onActivityResult: ");
            } catch (Exception e) {

                e.printStackTrace();
            }
        }


    }


    private void UploadAddhaarDataApi() {
        showLoading(context, "Please Wait");
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CommonData.UploadAadhaarData, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.e("upload_response", response + "");
                hideLoading();
                try {
                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("200")) {

                        Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(Verifyaadhar.this, Bankdetails.class);
                        startActivity(intent);
                        // finishAffinity();





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

                hashMap.put("user_id", DriverID);
                hashMap.put("adhar_no", AadhaarNo);



                Log.e("params",hashMap+"");

                return hashMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();

                params.put("adhar_front", new DataPart(System.currentTimeMillis() + ".png", getFileDataFromDrawable(AadhaaarFrontBitmap)));
                params.put("adhar_back", new DataPart(System.currentTimeMillis() + ".png", getFileDataFromDrawable(AadhaaarBackBitmap)));
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