package com.codediffusion.tyidedriver.Extra;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;

import com.codediffusion.tyidedriver.R;

public class CommonData {
    private static ProgressDialog processing;
    public static String BASE_URL="https://aduetechnologies.com/tyide/api/";



    public static String UserRagisterData=BASE_URL+"driver-register.php";
    public static String UploadDlData=BASE_URL+"driver-license.php";
    public static String UploadAadhaarData=BASE_URL+"add-adhar.php";
    public static String UploadDriverProfileData=BASE_URL+"driver-update-profile.php";


    public static String UserData="USERDATA";

    public static String UserName="USERName";
    public static String UserOtp="USEROTP";
    public static String UserMobile="USERMOBILE";
    public static String UserEmail="USEREMAIL";
    public static String DriverID="DRIVERID";
    public static String PickUpLocation="PICKUPLOCATION";
    public static String DropLocation="DROPLOCATION";
    public static String TripType="TRIPTYPE";
    public static String PickupDate="PICKUPDATE";
    public static String DropDate="DROPDATE";
    public static String PickUpTime="PICKUPTIME";
    public static String TotalAmount="TOTALAMOUNT";
    public static String TempararyId="TEMPARARYID";
    public static String PayAmount="PAYAMOUNT";






    public static void showLoading(Context mContext, String message) {

        if(mContext != null){

            processing = new ProgressDialog(mContext);
            processing.setMessage(message);
            processing.setCancelable(false);
            processing.setCanceledOnTouchOutside(false);
            processing.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            processing.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.custom_progress_dialog));

            //mContext.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
            //  processing.setIndeterminateDrawable(mContext.getDrawable(R.drawable.custom_progress_dialog));
            processing.setIndeterminate(false);
            processing.setCanceledOnTouchOutside(false);

            if (!processing.isShowing()) {
                processing.show();
            }

        }

    }

    public static void hideLoading() {
        if (processing != null)
            processing.dismiss();
    }


    public static String[] Location_Permision = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION

    };


    public static String[] Storage_Permision = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

    };



}
