package com.codediffusion.tyidedriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codediffusion.tyidedriver.Model.modelCompleteOrder;
import com.codediffusion.tyidedriver.Model.modelCurrentOrder;
import com.codediffusion.tyidedriver.R;
import com.codediffusion.tyidedriver.databinding.LayoutCancelledBinding;
import com.codediffusion.tyidedriver.databinding.LayoutCompleteOrderBinding;

import java.util.List;

public class AdapterDriverCompleteOder extends RecyclerView.Adapter<AdapterDriverCompleteOder.CurrentHolder> {
   private List<modelCompleteOrder.Datum>currentOrder;
  // private List<modelCompleteOrder>currentOrderReview;
   private Context context;

    public AdapterDriverCompleteOder(List<modelCompleteOrder.Datum> currentOrder, Context context) {
        this.currentOrder = currentOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDriverCompleteOder.CurrentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_complete_order,parent,false);


       return new CurrentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDriverCompleteOder.CurrentHolder holder, int position) {

        modelCompleteOrder.Datum model=currentOrder.get(position);

        holder.binding.tvUserName.setText(model.getUserName());
        holder.binding.tvBookingDate.setText(model.getDepartureDate());
        holder.binding.tvBookingAmount.setText(context.getString(R.string.Rs)+" "+model.getYourPrice());
        holder.binding.tvPickUpLocation.setText(model.getFromPlace());
        holder.binding.tvDropLocation.setText(model.getToPlace());
        holder.binding.tvReviewName.setText(model.getName());
        holder.binding.tvUserReview.setText(model.getReview());
        holder.binding.Ratingbar.setScore(Float.parseFloat(model.getRating()));
    }

    @Override
    public int getItemCount() {
        return currentOrder.size();
    }

    public class CurrentHolder extends RecyclerView.ViewHolder {
        private LayoutCompleteOrderBinding binding;

        public CurrentHolder(@NonNull View itemView) {
            super(itemView);

            binding= DataBindingUtil.bind(itemView);

            if (binding!=null){
                binding.executePendingBindings();

            }



        }
    }
}
