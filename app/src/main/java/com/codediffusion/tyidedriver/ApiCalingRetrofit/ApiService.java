package com.codediffusion.tyidedriver.ApiCalingRetrofit;


import com.codediffusion.tyidedriver.Model.CoomonModel;
import com.codediffusion.tyidedriver.Model.modelAddWalletAmount;
import com.codediffusion.tyidedriver.Model.modelBankDetails;
import com.codediffusion.tyidedriver.Model.modelCompleteOrder;
import com.codediffusion.tyidedriver.Model.modelCurrentOrder;
import com.codediffusion.tyidedriver.Model.modelDriverLogin;
import com.codediffusion.tyidedriver.Model.modelGetDriverProfile;
import com.codediffusion.tyidedriver.Model.modelNewBooking;
import com.codediffusion.tyidedriver.Model.modelUserStatus;
import com.codediffusion.tyidedriver.Model.modelUserWalletAmount;
import com.codediffusion.tyidedriver.Model.modelWalletHistory;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


   /* @FormUrlEncoded
    @POST("update-password.php")
    Single<modelChangePassword> ChangePassData(@FieldMap HashMap<String, Object> hashMap);

*/
///// for Get Method

  /*  @GET(Api.CityListData_Api)
    Single<LocationModel> CircleAreaList();*/



    @FormUrlEncoded
    @POST("driver-login.php")
    Single<modelDriverLogin> UserLoginData(@FieldMap HashMap<String, Object> hashMap);


    /*@FormUrlEncoded
    @POST("driver-register.php")
    Single<modelRegistered> UserRegisterData(@FieldMap HashMap<String, Object> hashMap);
*/

    @FormUrlEncoded
    @POST("driver-kyc.php")
    Single<modelBankDetails> UserBenkDetailsData(@FieldMap HashMap<String, Object> hashMap);


    @FormUrlEncoded
    @POST("driver-verify.php")
    Single<modelUserStatus> UserStatusData(@FieldMap HashMap<String, Object> hashMap);


    @FormUrlEncoded
    @POST("driver-all-booking.php")
    Single<modelNewBooking> DriverNewBookingData(@FieldMap HashMap<String, Object> hashMap);



    @FormUrlEncoded
    @POST("booking-assign-to-driver.php")
    Single<CoomonModel> BooingAcceptData(@FieldMap HashMap<String, Object> hashMap);



    @FormUrlEncoded
    @POST("driver-get-driver-details.php")
    Single<modelGetDriverProfile> GetProfileData(@FieldMap HashMap<String, Object> hashMap);


    @FormUrlEncoded
    @POST("driver-get-wallet-amount.php")
    Single<modelUserWalletAmount> GetWalletAmountData(@FieldMap HashMap<String, Object> hashMap);



    @FormUrlEncoded
    @POST("driver-credit-debit-money.php")
    Single<modelAddWalletAmount> GetWalletRecharge(@FieldMap HashMap<String, Object> hashMap);


    @FormUrlEncoded
    @POST("driver-wallet-history.php")
    Single<modelWalletHistory> GetWalletHistory(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("driver-booking-history.php")
    Single<modelCurrentOrder> CurrentOrder(@FieldMap HashMap<String, Object> hashMap);


    @FormUrlEncoded
    @POST("driver-booking-history.php")
    Single<modelCompleteOrder> CompletetOrder(@FieldMap HashMap<String, Object> hashMap);

}
