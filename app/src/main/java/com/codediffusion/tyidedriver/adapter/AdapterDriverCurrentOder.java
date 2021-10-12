package com.codediffusion.tyidedriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codediffusion.tyidedriver.Model.modelCurrentOrder;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.databinding.LayoutCurrentOrderBinding;

import java.util.List;

public class AdapterDriverCurrentOder extends RecyclerView.Adapter<AdapterDriverCurrentOder.CurrentHolder> {
   private List<modelCurrentOrder.Datum>currentOrder;
   private Context context;

    public AdapterDriverCurrentOder(List<modelCurrentOrder.Datum> currentOrder, Context context) {
        this.currentOrder = currentOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDriverCurrentOder.CurrentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_current_order,parent,false);


       return new CurrentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDriverCurrentOder.CurrentHolder holder, int position) {

        modelCurrentOrder.Datum model=currentOrder.get(position);

        holder.binding.tvUserName.setText(model.getUserName());
        holder.binding.tvBookingDate.setText(model.getDepartureDate());
        holder.binding.tvBookingAmount.setText(context.getString(R.string.Rs)+" "+model.getYourPrice());
        holder.binding.tvPickUpLocation.setText(model.getFromPlace());
        holder.binding.tvDropLocation.setText(model.getToPlace());
        holder.binding.tvUserMobile.setText(model.getUserName());

    }

    @Override
    public int getItemCount() {
        return currentOrder.size();
    }

    public class CurrentHolder extends RecyclerView.ViewHolder {
        private LayoutCurrentOrderBinding binding;

        public CurrentHolder(@NonNull View itemView) {
            super(itemView);

            binding= DataBindingUtil.bind(itemView);

            if (binding!=null){
                binding.executePendingBindings();

            }



        }
    }
}
