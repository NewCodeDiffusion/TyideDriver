package com.codediffusion.tyidedriver.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codediffusion.tyidedriver.ApiCalingRetrofit.GlobalClassApiCall;
import com.codediffusion.tyidedriver.Extra.CommonData;
import com.codediffusion.tyidedriver.Model.modelNewBooking;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.databinding.LayoutNewBookingBinding;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codediffusion.tyidedriver.Extra.CommonData.hideLoading;

public class AdapterNewBooking extends RecyclerView.Adapter<AdapterNewBooking.NewBookingHolder> {
    private List<modelNewBooking.Datum>newBookingList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String DriverId;


    public UpdateList updateList;

    public interface UpdateList{
        void UpdateData();
    }


    private CompositeDisposable disposable=new CompositeDisposable();

    public AdapterNewBooking(List<modelNewBooking.Datum> newBookingList, Context context, UpdateList updateList) {
        this.newBookingList = newBookingList;
        this.context = context;
        this.updateList = updateList;
    }

    @NonNull
    @Override
    public AdapterNewBooking.NewBookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_new_booking,parent,false);


        sharedPreferences=context.getSharedPreferences(CommonData.UserData,Context.MODE_PRIVATE);

        if (sharedPreferences.contains(CommonData.DriverID)){
             DriverId=sharedPreferences.getString(CommonData.DriverID,"");
        }


        return new NewBookingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNewBooking.NewBookingHolder holder, int position) {
        modelNewBooking.Datum model=newBookingList.get(position);

        Glide.with(context).load(model.getUserImg()).into(holder.binding.ivUserImage);

        holder.binding.tvUserName.setText(model.getUserName());
        holder.binding.tvBookingFullAmount.setText(context.getString(R.string.Rs)+" "+model.getBookingAmount());
        holder.binding.tvCompanyPerAmout.setText(context.getString(R.string.Rs)+" "+model.getCompanyCommission());
        holder.binding.tvDriverAmount.setText(context.getString(R.string.Rs)+" "+model.getYourPrice());
        holder.binding.tvPickUpLocation.setText(model.getFromPlace());
        holder.binding.tvDropLocation.setText(model.getToPlace());
        holder.binding.tvTripStartDate.setText(model.getDepartureDate());
        holder.binding.tvTripEndDate.setText(model.getReturnDate());
        holder.binding.tvCarModel.setText(model.getCarName());
        holder.binding.tvCarType.setText(model.getCarType());
        holder.binding.tvNote.setText(model.getNote());
        holder.binding.tvSelectPackage.setText(model.getPackageName());
        holder.binding.tvTripType.setText(model.getTripType());
        holder.binding.tvTripFor.setText(model.getTripFor());
        holder.binding.tvTotalDistsnce.setText(model.getRunningDistance()+" "+"Km");



        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptBokingApi(model.getBookingTableId());
            }
        });




    }


    @Override
    public int getItemCount() {
        return newBookingList.size();
    }

    public class NewBookingHolder extends RecyclerView.ViewHolder {

        private LayoutNewBookingBinding binding;
        public NewBookingHolder(@NonNull View itemView) {
            super(itemView);


            binding= DataBindingUtil.bind(itemView);
            if (binding!=null){
                binding.executePendingBindings();
            }
        }
    }

    private void AcceptBokingApi(String bookingTableId) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("driver_id", DriverId);
        hashMap.put("booking_table_id", bookingTableId);



        Log.e("params",hashMap+"");

///

        disposable.add(GlobalClassApiCall.initRetrofit().BooingAcceptData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((user, throwatble) -> {
                           // hideLoading();
                            if (user != null) {

                                Log.e("jsdrikfhjkfh","Response size: " + new Gson().toJson(user)+"");


                                if (user.getStatus().equals("200")) {

                                    Toast.makeText(context, user.getMessage()+"", Toast.LENGTH_LONG).show();

                                    updateList.UpdateData();

                                }else{
                                    Toast.makeText(context, user.getMessage()+"", Toast.LENGTH_SHORT).show();

                                }
                            }else{

                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                )
        );

    }



}
