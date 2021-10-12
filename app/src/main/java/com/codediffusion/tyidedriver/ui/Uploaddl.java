package com.codediffusion.tyidedriver.ui;

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
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.Verifyaadhar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;
import static com.codediffusion.tyidedriver.Extra.CommonData.showLoading;

public class Uploaddl extends AppCompatActivity {

    private Button loogin;
    private ActionBar actionBar;
    private ImageView Iv_Back,img_DlF,img_DlB,img_PCF,img_PCB,iv_dlFrontImage,iv_dlBackImage,iv_painCardFront,iv_painCardBack;

    private Bitmap dlFrontImageBitmap,dlBackImageBitmap,painCardFrontBitmap,painCardBackBitmap;

    private String DlNo,PanCardNo,DriverID;
    private EditText et_DlNo,et_PanCArdNo;


    private String SelectImageType;
    private int PICK_IMAGE_GALLERY = 100;
    private int PICK_IMAGE_CAMERA = 101;
    private Bitmap bitmap;
    private SharedPreferences sharedPreferences;
    private Uploaddl activity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaddl);
        actionBar =getSupportActionBar();
        actionBar.hide();
        context=activity=this;


        changestatusbarcolor();


        sharedPreferences=getSharedPreferences(CommonData.UserData,MODE_PRIVATE);
        if (sharedPreferences.contains(CommonData.DriverID)){
            DriverID=sharedPreferences.getString(CommonData.DriverID,"");
        }


        Initialization();


    }

    private void Initialization() {
        loogin = findViewById(R.id.next);
        Iv_Back = findViewById(R.id.Iv_Back);
        et_DlNo = findViewById(R.id.et_DlNo);
        et_PanCArdNo = findViewById(R.id.et_PanCArdNo);

        img_DlF = findViewById(R.id.img_DlF);
        img_DlB = findViewById(R.id.img_DlB);
        img_PCF = findViewById(R.id.img_PCF);
        img_PCB = findViewById(R.id.img_PCB);
        iv_dlFrontImage = findViewById(R.id.iv_dlFrontImage);
        iv_dlBackImage = findViewById(R.id.iv_dlBackImage);
        iv_painCardFront = findViewById(R.id.iv_painCardFront);
        iv_painCardBack = findViewById(R.id.iv_painCardBack);



        loogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(Uploaddl.this, Verifyaadhar.class);
                startActivity(intent);*/

                VAlidation();

            }
        });

        Iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        img_DlF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageType="DLFront";
                ChooseImageDialog(SelectImageType);
            }
        });

        img_DlB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageType="DLBack";
                ChooseImageDialog(SelectImageType);
            }
        });

        img_PCF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageType="PanCardFront";
                ChooseImageDialog(SelectImageType);
            }
        });

        img_PCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageType="PanCardBack";
                ChooseImageDialog(SelectImageType);
            }
        });


    }

    private void VAlidation() {

        DlNo=et_DlNo.getText().toString();
        PanCardNo=et_PanCArdNo.getText().toString();


        if (TextUtils.isEmpty(DlNo)){
            et_DlNo.setError("Enter Dl No");
            et_DlNo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PanCardNo)){
            et_PanCArdNo.setError("Enter PanCard No");
            et_PanCArdNo.requestFocus();
            return;
        }

        if (dlFrontImageBitmap==null){
            Toast.makeText(getApplicationContext(), "Upload Dl Front Image", Toast.LENGTH_SHORT).show();
            return;
        }
       /* if (dlBackImageBitmap==null){
            Toast.makeText(getApplicationContext(), "Upload Dl Back Image", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (painCardFrontBitmap==null){
            Toast.makeText(getApplicationContext(), "Upload Pan Card Front Image", Toast.LENGTH_SHORT).show();
            return;
        }
       /* if (painCardBackBitmap==null){
            Toast.makeText(getApplicationContext(), "Upload Pan Card Back Image", Toast.LENGTH_SHORT).show();
            return;
        }*/


        UpoadDocumentApi();



    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changestatusbarcolor(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.lightgraan));
    }


    private void ChooseImageDialog(String DocumentType) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Uploaddl.this);
        builderSingle.setTitle("Choose Image");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(Uploaddl.this,
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

                if (SelectImageType.equalsIgnoreCase("DLFront")){
                    iv_dlFrontImage.setImageBitmap(bitmap);
                    dlFrontImageBitmap=bitmap;
                }

                if (SelectImageType.equalsIgnoreCase("DLBack")){
                    iv_dlBackImage.setImageBitmap(bitmap);
                    dlBackImageBitmap=bitmap;
                }
                if (SelectImageType.equalsIgnoreCase("PanCardFront")){
                    iv_painCardFront.setImageBitmap(bitmap);
                    painCardFrontBitmap=bitmap;
                }
                if (SelectImageType.equalsIgnoreCase("PanCardBack")){
                    iv_painCardBack.setImageBitmap(bitmap);
                    painCardBackBitmap=bitmap;
                }





            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {
            try {

                bitmap = (Bitmap) data.getExtras().get("data");

                if (SelectImageType.equalsIgnoreCase("DLFront")){
                    iv_dlFrontImage.setImageBitmap(bitmap);
                    dlFrontImageBitmap=bitmap;
                }

                if (SelectImageType.equalsIgnoreCase("DLBack")){
                    iv_dlBackImage.setImageBitmap(bitmap);
                    dlBackImageBitmap=bitmap;
                }
                if (SelectImageType.equalsIgnoreCase("PanCardFront")){
                    iv_painCardFront.setImageBitmap(bitmap);
                    painCardFrontBitmap=bitmap;
                }
                if (SelectImageType.equalsIgnoreCase("PanCardBack")){
                    iv_painCardBack.setImageBitmap(bitmap);
                    painCardBackBitmap=bitmap;
                }


                Log.e("Image Path", "onActivityResult: ");
            } catch (Exception e) {

                e.printStackTrace();
            }
        }


    }


    private void UpoadDocumentApi() {
        showLoading(context, "Please Wait");
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CommonData.UploadDlData, new Response.Listener<NetworkResponse>() {
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


                        Intent intent = new Intent(Uploaddl.this, Verifyaadhar.class);
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
                hashMap.put("dl_no", DlNo);
                hashMap.put("pan", PanCardNo);


                Log.e("params",hashMap+"");

                return hashMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();

                params.put("licance_img", new DataPart(System.currentTimeMillis() + ".png", getFileDataFromDrawable(dlFrontImageBitmap)));
                params.put("pan_img", new DataPart(System.currentTimeMillis() + ".png", getFileDataFromDrawable(painCardFrontBitmap)));
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